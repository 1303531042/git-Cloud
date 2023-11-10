package com.ruoyi.iot.energy.model.bo;

import lombok.Data;

import java.util.Date;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description:
 */
@Data
public class QueryEnergyBo {
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
     * 统计 0-同比 1-环比
     */
    private Integer statisticType;

    /**
     * 起始时间
     */
    private Date startTime;
}
