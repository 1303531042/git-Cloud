package com.ruoyi.common.influxdb.wrapper.query;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.enumeration.FluxTemplate;
import com.ruoyi.common.influxdb.util.InfluxTimeFormate;
import com.ruoyi.common.influxdb.util.TemplateUtil;
import com.ruoyi.common.utils.reflect.ReflectUtils;
import lombok.Data;
import lombok.Getter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther: KUN
 * @date: 2023/7/14
 * @description:
 */
public class SimpleQueryWrapper<W extends Measudirection> extends AbstractFunctionQueryFluxWrapper<W>  {

    private StringBuilder filterBuild = new StringBuilder();

    private boolean useRange = false;

    private boolean useColumn = false;

    @Getter
    private Date startTime;
    @Getter
    private Date endTime;

    private Map<String, String> filterMap = new HashMap<>();


    public SimpleQueryWrapper(Class<W> MeasudirectionClass) {
        super(MeasudirectionClass);
    }





    /**
     * 选择要操作influx的桶
     *
     * @param bucketName 桶名
     */
    @Override
    public FluxQueryWrapper<W> bucket(String bucketName) {
        StringBuilder builder = new StringBuilder(FluxTemplate.BUCKET_FLUX.getValue());
        TemplateUtil.replaceTemplate(builder, "bucket", bucketName);
        addTemplate(FluxTemplate.BUCKET_FLUX, builder.toString());
        return this;
    }

    /**
     * 指定查询时间范围
     *
     * @param startTime 起始时间
     * @param endTime   结束时间
     */
    @Override
    public FluxQueryWrapper<W> rangeTime(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        String start = InfluxTimeFormate.getFormate(startTime);
        String end = InfluxTimeFormate.getFormate(endTime);
        StringBuilder builder = new StringBuilder(FluxTemplate.RANGE_TIME_FLUX.getValue());
        TemplateUtil.replaceTemplate(builder, "startDate", start);
        TemplateUtil.replaceTemplate(builder, "endDate", end);
        addTemplate(FluxTemplate.RANGE_TIME_FLUX, builder.toString());
        useRange();
        return this;
    }

    /**
     * 指定查询表
     *
     * @param measurementName 表名
     */
    @Override
    public FluxQueryWrapper<W> measurement(String measurementName) {
        StringBuilder builder = new StringBuilder(FluxTemplate.MEASUREMENT_FLUX.getValue());
        TemplateUtil.replaceTemplate(builder, "measurement", measurementName);
        addTemplate(FluxTemplate.MEASUREMENT_FLUX, builder.toString());
        return this;
    }

    /**
     * 过滤器 and
     *
     * @param tag
     * @param tagValue
     * @return
     */
    @Override
    public FluxQueryWrapper<W> andFilter(String tag, String tagValue) {
        addUseTag(tag);
        filterMap.put(tag, tagValue);
        StringBuilder builder = new StringBuilder(FluxTemplate.AND_FILTER_FLUX.getValue());
        TemplateUtil.replaceTemplate(builder, "tagValue", tagValue);
        TemplateUtil.replaceTemplate(builder, "tag", tag);
        filterBuild.append(builder);
        return this;
    }

    /**
     * 过滤器or
     *
     * @param tag
     * @param tagValue
     * @return
     */
    @Override
    public FluxQueryWrapper<W> orFilter(String tag, String tagValue) {
        throw new RuntimeException("orFilter方法暂时不支持");
    }

    /**
     * 选择输出列 or
     *
     * @param column
     * @return
     */
    @Override
    public FluxQueryWrapper<W> orColumn(String column) {
        addUseColumn(column);
        StringBuilder builder = new StringBuilder(FluxTemplate.COLUMN_FLUX.getValue());
        TemplateUtil.replaceTemplate(builder, "columnName", column);
        addTemplate(FluxTemplate.COLUMN_FLUX, builder.toString());
        useColumn();
        return this;
    }

    private void toFilter() {
        if (!(filterBuild.length() == 0)) {
            StringBuilder builder = new StringBuilder(FluxTemplate.FILTER_FLUX.getValue());
            TemplateUtil.replaceTemplate(builder, "filters", filterBuild.toString().replaceFirst("and", ""));
            addTemplate(FluxTemplate.FILTER_FLUX, builder.toString());
        }
    }

    @Override
    public String getTemplate() {
        toFilter();
        if (!checkMustBe()) {
            throw new RuntimeException("range和column未使用");
        }
        return super.getTemplate();
    }

    @Override
    public String getMeasurement() {
        return getDefaultMeasurement();
    }

    private void useColumn() {
        useColumn = true;
    }
    private void useRange() {
        useRange = true;
    }
    /**
     * 是否设置了range
     */
   private boolean checkRange(){
       return useRange;
   }


    /**
     * 是否设置了column
     */
    private boolean checkColumn(){
        return useColumn;
    }

    @Override
    public boolean checkMustBe() {
        if (checkRange() & checkColumn()) {
            return true;
        }
        return false;
    }

    /**
     * 获取该wrapper 过滤器的值
     */
    public String getFilterValue(String filterName) {
        return filterMap.get(filterName);
    }





}
