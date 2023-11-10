package com.ruoyi.common.influxdb;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

/**
 * @auther: KUN
 * @date: 2023/8/8
 * @description:
 */
@Data
@AllArgsConstructor
public class TimeWrapper {
    private Date startTime;
    private Date endTime;
}
