package com.ruoyi.common.exception.influx;

/**
 * @auther: KUN
 * @date: 2023/7/13
 * @description: 查询时间未设置
 */
public class RangeTimeNotSetException extends InfluxException{
    public RangeTimeNotSetException() {
        super("查询时间未设置");
    }

}
