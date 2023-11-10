package com.ruoyi.common.influxdb.wrapper;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.ruoyi.common.exception.influx.WrapperNotFoundAnnotationException;
import com.ruoyi.common.influxdb.Bucket;
import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.enumeration.FluxTemplate;
import com.ruoyi.common.influxdb.kv.DataType;
import com.ruoyi.common.influxdb.util.TemplateUtil;
import com.ruoyi.common.utils.reflect.ReflectUtils;
import org.springframework.core.ParameterizedTypeReference;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther: KUN
 * @date: 2023/7/10
 * @description: 包装器抽象类
 */
public abstract class AbstractFluxWrapper<W extends Measudirection>implements FluxWrapper<W> {
    private final Class<W> MeasudirectionClass;

    public AbstractFluxWrapper(Class<W> MeasudirectionClass) {
        this.MeasudirectionClass = MeasudirectionClass;

        analyzeMeasudirection();
    }

    /**
     * 存储Measurement主键
     */
    private final List<String> tagList = new ArrayList<>();

    /**
     * 存储Measurement列值
     * key；列值名
     * value: 类型
     */
    private final Map<String,DataType> columnMap = new HashMap<>();

    /**
     * Measudirections上指定的默认桶
     */
    private  String defaultBucket;

    /**
     * Measudirections上指定的默认表
     */
    private String defaultMeasurement;



    /**
     * 解析 Measudirections 表对象 分析tag以及value
     */
    private void analyzeMeasudirection() {

        //获取泛型类
        scanClassAnnotation(MeasudirectionClass);
        scanFieldAnnotation(MeasudirectionClass);
    }

    /**
     * 扫描Measudirections 属性上的tag注解和Column注解
     * @param classGenricType
     */
    private void scanFieldAnnotation(Class<?> classGenricType) throws WrapperNotFoundAnnotationException{
        List<Field> fieldList = Arrays.stream(classGenricType.getDeclaredFields()).collect(Collectors.toList());
        fieldList.forEach(field -> {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                if (column.tag()) {
                    tagList.add(field.getName());
                } else if (!column.timestamp()) {
                    columnMap.put(field.getName(), DataType.getInstance(field.getType()));
                }

            } else {
                throw new WrapperNotFoundAnnotationException(field.getName()+"属性"+Column.class.getName());
            }
        });
    }

    /**
     * 扫描Measudirections类上的bucket注解和Measurement注解
     * @param classGenricType
     */
    private void scanClassAnnotation(Class<?> classGenricType)throws WrapperNotFoundAnnotationException {
        List<Annotation> annotationList = Arrays.stream(classGenricType.getAnnotations()).collect(Collectors.toList());
        boolean havBucket = false;
        boolean havMeasurement = false;
        for (Annotation annotation : annotationList) {
            if (annotation instanceof Bucket) {
                havBucket = true;
                this.defaultBucket = ((Bucket) annotation).value();
            } else if (annotation instanceof Measurement) {
                havMeasurement = true;
                this.defaultMeasurement = ((Measurement) annotation).name();
        }
        }
        if (!havBucket) {
            throw new WrapperNotFoundAnnotationException(Bucket.class.getName());
        }
        if (!havMeasurement) {
            throw new WrapperNotFoundAnnotationException(Measurement.class.getName());

        }
    }


    /**
     *
     * @param columnNames column 名字列表
     * @return 该column是否是Number类型 只有Number类型的才能调用函数
     */
    protected boolean columnInstanceofNumber(String... columnNames) {
        List<String> columnNameList = Arrays.stream(columnNames).collect(Collectors.toList());
        for (String columnName : columnNameList) {
            Class<?> columnClass = columnMap.get(columnName).getValue();
            List<Class<?>> allInterfaces = ReflectUtils.getAllInterfaces(columnClass);
            if (!allInterfaces.contains(Number.class)) {
                return false;
            }
        }
        return true;
    }



    /**
     * 获取默认的桶
     * @return
     */
    protected String getDefaultBucket() {
        return defaultBucket;
    }

    /**
     * 获取默认的表
     * @return
     */
    protected String getDefaultMeasurement() {
        return defaultMeasurement;
    }


    /**
     * 获取该column的类型
     *
     * @param columnName
     * @return
     */
    public Class<?> getColumnClass(String columnName) {
        if (columnMap.containsKey(columnName)) {
            return columnMap.get(columnName).getValue();
        }
        return null;
    }

    protected List<String> getTagList() {
        return tagList;
    }

    protected Map<String, DataType> getColumnMap() {
        return columnMap;
    }
}
