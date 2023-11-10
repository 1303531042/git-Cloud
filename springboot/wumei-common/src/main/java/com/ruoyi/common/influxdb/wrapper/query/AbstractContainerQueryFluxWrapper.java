package com.ruoyi.common.influxdb.wrapper.query;

import com.ruoyi.common.exception.influx.ColumnNotExistException;
import com.ruoyi.common.exception.influx.FunctionCanNotExecException;
import com.ruoyi.common.exception.influx.TagNotExistException;
import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.enumeration.FluxTemplate;
import com.ruoyi.common.influxdb.wrapper.AbstractFluxWrapper;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther: KUN
 * @date: 2023/7/10
 * @description: 查询包装抽象类
 */
public abstract class AbstractContainerQueryFluxWrapper<W extends Measudirection> extends AbstractFluxWrapper<W> implements FluxQueryWrapper<W> {

    /**
     * 此次执行所涉及的column列表
     */
    private List<String> useColumnList = new ArrayList<>();

    /**
     * 此次执行所涉及的tag列表
     */
    private List<String> useTagNameList = new ArrayList<>();

    /**
     * key: flux模版
     * value: 存储值
     * LinkedHashMap保证有序性
     */
    private final Map<FluxTemplate, StringBuilder> fluxMap = new LinkedHashMap<>();

    public AbstractContainerQueryFluxWrapper(Class<W> MeasudirectionClass) {
        super(MeasudirectionClass);
        initDefaultFluxTemplate();

    }


    private void initDefaultFluxTemplate() {
        fluxMap.put(FluxTemplate.ZONE, new StringBuilder(FluxTemplate.ZONE.getValue()));
        fluxMap.put(FluxTemplate.BUCKET_FLUX, new StringBuilder());
        fluxMap.put(FluxTemplate.RANGE_TIME_FLUX, new StringBuilder());
        fluxMap.put(FluxTemplate.COLUMN_FLUX, new StringBuilder());
        fluxMap.put(FluxTemplate.FILTER_FLUX, new StringBuilder());
        fluxMap.put(FluxTemplate.WINDOW_FLUX, new StringBuilder());
        fluxMap.put(FluxTemplate.FUNCTION_FLUX, new StringBuilder());
        bucket(getDefaultBucket());
        measurement(getDefaultMeasurement());
    }

    /**
     * 检查该wrpper 使用的column列表是否存在
     *
     * @throws ColumnNotExistException 该column 在Measudirections中不存在
     */
    protected void checkColumnExist() throws ColumnNotExistException {
        for (String columnName : useColumnList) {
            if (!getColumnMap().containsKey(columnName)) {
                throw new ColumnNotExistException(columnName);
            }
        }

    }

    /**
     * 检查该wrpper 使用的tag列表是否存在
     *
     * @throws ColumnNotExistException 该column 在Measudirections中不存在
     */
    protected void checkTagExist() throws ColumnNotExistException {
        for (String tagName : useTagNameList) {
            if (!getTagList().contains(tagName)) {
                throw new TagNotExistException(tagName);
            }
        }
    }

    /**
     * 检查输出列是否属于number
     *
     * @throws FunctionCanNotExecException
     */
    public void checkColumnTypeIsInstanceNumber() throws FunctionCanNotExecException {
        for (String column : useColumnList) {
            if (!Arrays.stream(getColumnMap().get(column).getValue().getInterfaces()).collect(Collectors.toList()).contains(Number.class)) {
//                throw new FunctionCanNotExecException();
            }
        }
    }


    /**
     * 添加使用的column
     *
     * @param columnName column名
     */
    protected void addUseColumn(String columnName) {
        if (!useColumnList.contains(columnName)) {
            this.useColumnList.add(columnName);

        }
    }

    /**
     * 添加使用的 tag
     *
     * @param tagName tag名
     */
    protected void addUseTag(String tagName) {
        if (!useTagNameList.contains(tagName)) {
            this.useTagNameList.add(tagName);
        }
    }

    /**
     * 获取使用的输出Columns
     *
     * @return
     */
    protected List<String> getUseColumns() {
        return useColumnList;
    }

    /**
     * 覆盖某一个模版的值
     *
     * @param template
     * @param value
     */
    protected void addTemplate(FluxTemplate template, String value) {
        StringBuilder builder = fluxMap.computeIfAbsent(template, key -> new StringBuilder());
        builder.setLength(0);
        builder.append(value);
    }

    /**
     * 增加某一个模版的值
     *
     * @param template
     * @param value
     */
    protected void andAddTemplate(FluxTemplate template, String value) {
        StringBuilder builder = fluxMap.computeIfAbsent(template, key -> new StringBuilder());
        builder.append(value);
    }

    public String getTemplate() {
        checkColumnExist();
        checkTagExist();
        StringBuilder result = new StringBuilder();
        fluxMap.values().forEach(result::append);
        return result.toString();

    }


}
