package com.ruoyi.common.influxdb.wrapper.query;


import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.enumeration.WindowFunction;
import com.ruoyi.common.influxdb.enumeration.WindowTimeIntervalUnit;
import com.ruoyi.common.influxdb.wrapper.FluxWrapper;
import java.util.Date;

/**
 * @auther: KUN
 * @date: 2023/7/14
 * @description: 查询包装接口
 */
public interface FluxQueryWrapper<W extends Measudirection> extends FluxWrapper<W>  {
    /**
     *
     * @param time 时间
     * @param unit 单位
     * @param function 聚合函数
     * @return
     */
    FluxQueryWrapper<W> window(Integer time, WindowTimeIntervalUnit unit, WindowFunction function);



    /**
     * 累计函数
     * @return
     */
    FluxQueryWrapper<W> cumulativeSum();

    /**
     * 非负差异的累计和函数
     * @return
     */
    FluxQueryWrapper<W> increase();

    /**
     * 非空第一个函数
     * @return
     */
    FluxQueryWrapper<W> first();

    /**
     * 非空最后一个值
     * @return
     */
    FluxQueryWrapper<W> last();

    /**
     * 平均数函数
     */
    FluxQueryWrapper<W> mean();

    /**
     * 减去上一个值
     * @return
     */
    FluxQueryWrapper<W> difference();



    /**
     * 选择要操作influx的桶
     *
     * @param bucketName 桶名
     */
    FluxQueryWrapper<W> bucket(String bucketName);

    /**
     * 指定查询时间范围
     * @param startTime 起始时间
     * @param endTime   结束时间
     */
    FluxQueryWrapper<W> rangeTime(Date startTime, Date endTime);

    /**
     * 指定查询表
     * @param measurementName 表名
     */
    FluxQueryWrapper<W> measurement(String measurementName);

    /**
     * 过滤器 and
     * @param tag
     * @param tagValue
     * @return
     */
    FluxQueryWrapper<W> andFilter(String tag, String tagValue);

    /**
     * 过滤器or
     * @param tag
     * @param tagValue
     * @return
     */
    FluxQueryWrapper<W> orFilter(String tag, String tagValue);

    /**
     * 选择输出列 or
     * @param column
     * @return
     */
    FluxQueryWrapper<W> orColumn(String column);

    /**
     * 是否使用了方法
     */
    boolean useFunction();
    boolean useArgFunction();


    /**
     * 检查必填项是否全部设置
     */
    boolean checkMustBe();

    String getTemplate();

    String getMeasurement();

    Date getStartTime();

    Date getEndTime();





}
