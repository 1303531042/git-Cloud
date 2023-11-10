package com.ruoyi.iot.energy.model.bo;

import com.ruoyi.common.influxdb.enumeration.WindowFunction;
import com.ruoyi.common.influxdb.enumeration.WindowTimeIntervalUnit;
import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description: influx window
 */
@Data
public class InfluxWindowBo {
    /**
     * 窗口单位
     */
    private WindowTimeIntervalUnit unit;

    /**
     * 聚合时间
     */
    private Integer time;

    /**
     * 聚合方法
     */
    private WindowFunction function;

}
