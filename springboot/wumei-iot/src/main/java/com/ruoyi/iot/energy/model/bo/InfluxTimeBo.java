package com.ruoyi.iot.energy.model.bo;

import lombok.Data;

import java.util.Date;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description: 时间包装类
 */
@Data
public class InfluxTimeBo {
    /**
     * 查询起始时间
     */
    private Date startTime;
    /**
     * 查询结束时间
     */
    private Date endTime;
}
