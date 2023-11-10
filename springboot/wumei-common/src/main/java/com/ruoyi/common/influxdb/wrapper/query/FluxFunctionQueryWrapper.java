package com.ruoyi.common.influxdb.wrapper.query;

import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.enumeration.WindowFunction;
import com.ruoyi.common.influxdb.enumeration.WindowTimeIntervalUnit;

/**
 * @auther: KUN
 * @date: 2023/7/14
 * @description: 方法函数查询包装接口
 */
public interface FluxFunctionQueryWrapper<W extends Measudirection> extends FluxQueryWrapper<W> {

    /**
     *
     * @param time 时间
     * @param unit 单位
     * @param function 聚合函数
     * @return
     */
    FluxFunctionQueryWrapper<W> window(Integer time, WindowTimeIntervalUnit unit, WindowFunction function);



    /**
     * 累计函数
     * @return
     */
    FluxFunctionQueryWrapper<W> cumulativeSum();

    /**
     * 非负差异的累计和函数
     * @return
     */
    FluxFunctionQueryWrapper<W> increase();

    /**
     * 非空第一个函数
     * @return
     */
    FluxFunctionQueryWrapper<W> first();

    /**
     * 非空最后一个值
     * @return
     */
    FluxFunctionQueryWrapper<W> last();

    /**
     * 平均数函数
     */
    FluxFunctionQueryWrapper<W> mean();


}
