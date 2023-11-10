package com.ruoyi.common.influxdb;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.influxdb.annotations.Column;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.ruoyi.common.influxdb.kv.entry.*;
import com.ruoyi.common.influxdb.wrapper.query.FluxQueryWrapper;
import com.ruoyi.common.utils.reflect.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther: KUN
 * @date: 2023/10/25
 * @description:
 */
@Slf4j
public abstract class InfluxBaseMapper<W extends Measudirection> {
    @Autowired
    private InfluxDBClient influxDBClient;
    private QueryApi queryApi;
    private WriteApi writeApi;
    @PostConstruct
    public void init(){
        queryApi = influxDBClient.getQueryApi();
        writeApi = influxDBClient.getWriteApi();
    }

    /**
     * 插入一行数据
     * @param entity
     * @return
     */
    public void insert(W entity){
        writeApi.writeMeasurement(WritePrecision.MS,entity);
    }
    /**
     * 插入多行数据
     * @return
     */
    public void insertBatch(List<W> entityList) {
        writeApi.writeMeasurements(WritePrecision.MS, entityList);
    }


    /**
     * 查询多行
     * @param queryWrapper
     * @return
     */
    public Map<W, List<TsKvEntry>> selectList(FluxQueryWrapper<W> queryWrapper) {
        Map<W, List<TsKvEntry>> resultMap = new HashMap<>();
        Class<W> clazz = currentVoClass();
        List<String> fieldNameList = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> {
                    Column column = field.getAnnotation(Column.class);
                    if (column != null&&column.tag()) {
                        return true;
                    }
                    return false;
                }).map(Field::getName).collect(Collectors.toList());
        try {
            log.info(queryWrapper.getTemplate());
             queryApi.query(queryWrapper.getTemplate()).forEach(fluxTable -> {
                //获取行数据信息
                List<FluxRecord> recordList = fluxTable.getRecords();
                FluxRecord templateRecord = recordList.get(0);
                //创建主键过滤对象
                W w = BeanUtils.instantiateClass(clazz);
                fieldNameList.forEach(fieldName->{
                    ReflectUtils.invokeSetter(w, fieldName, templateRecord.getValueByKey(fieldName));
                });
                //获取该时间段设备值
                List<TsKvEntry> tsKvEntries = recordList.stream().map(record -> new BasicTsKvEntry(Objects.requireNonNull(record.getTime()).toEpochMilli(), toKvEntry(record, queryWrapper))).collect(Collectors.toList());
                 resultMap.put(w, tsKvEntries);
            });
            return resultMap;
        } catch (Exception e) {
            log.warn("============================>" + queryWrapper.getTemplate());
            e.printStackTrace();
        }
        throw new RuntimeException();

    }

    /**
     * 查询单行
     *
     * @param queryWrapper
     * @return
     */
    public Map<W, TsKvEntry> selectOne(FluxQueryWrapper<W> queryWrapper) {
        Map<W, TsKvEntry> resultMap = new HashMap<>();
        Map<W, List<TsKvEntry>> map = this.selectList(queryWrapper);
        map.forEach((key,value) -> {
            if (value.size() > 1) {
                throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + value.size());
            } else {
                resultMap.put(key, value.get(0));
            }

        });
        return resultMap;
    }
    private KvEntry toKvEntry(FluxRecord record, FluxQueryWrapper<W> queryWrapper) {
        KvEntry kvEntry = null;
        String key = record.getField();
        Class<?> columnClass = queryWrapper.getColumnClass(key);
        if (columnClass != null) {
            if (String.class.equals(columnClass)) {
                kvEntry = new StringDataEntry(key, String.valueOf(record.getValue()));
            } else if (Long.class.equals(columnClass)) {
                kvEntry = new LongDataEntry(key, (Long) record.getValue());
            } else if (Boolean.class.equals(columnClass)) {
                kvEntry = new BooleanDataEntry(key, (Boolean) record.getValue());
            } else if (Double.class.equals(columnClass)) {
                kvEntry = new DoubleDataEntry(key, (Double) record.getValue());
            } else {
                log.warn("All values in key-value row are nullable ");
            }
        }
        return kvEntry;
    }
    private Class<W> currentVoClass() {
        return (Class<W>) ReflectionKit.getSuperClassGenericType(this.getClass(), InfluxBaseMapper.class, 0);
    }
}
