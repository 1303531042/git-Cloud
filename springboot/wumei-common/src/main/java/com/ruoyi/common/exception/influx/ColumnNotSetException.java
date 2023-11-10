package com.ruoyi.common.exception.influx;

/**
 * @auther: KUN
 * @date: 2023/7/13
 * @description:  输出列未设置
 */
public class ColumnNotSetException  extends InfluxException {
    public ColumnNotSetException() {
        super("查询时间未设置");
    }
}
