package com.ruoyi.common.influxdb.wrapper;

import com.ruoyi.common.influxdb.cache.container.Interval;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/8/14
 * @description:
 */
@Data
@AllArgsConstructor
public class IntervalWrapper {
    private Interval interval;
    private long intervalTime;
}
