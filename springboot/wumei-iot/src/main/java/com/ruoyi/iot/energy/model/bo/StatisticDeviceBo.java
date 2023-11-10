package com.ruoyi.iot.energy.model.bo;

import lombok.Data;

import java.util.Date;

/**
 * @auther: KUN
 * @date: 2023/9/26
 * @description:
 */
@Data
public class StatisticDeviceBo {
    /**
     * 设备id
     */
    private Integer deviceId;

    /**
     * 能源属性描述符号
     */
    private String identity;

    /**
     * 查询颗粒度 0-日 1-月 2-年
     */
    private Integer timeParticles;

    /**
     * 起始时间
     */
    private Date startTime;
}
