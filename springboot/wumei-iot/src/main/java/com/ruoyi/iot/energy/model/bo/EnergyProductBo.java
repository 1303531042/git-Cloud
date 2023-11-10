package com.ruoyi.iot.energy.model.bo;

import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/10/23
 * @description:
 */
@Data
public class EnergyProductBo {
    /**
     * 能源类型
     */
    private Integer energyType;
    /**
     * 产品id
     */
    private Integer productId;
    /**
     * 设备id
     */
    private Integer deviceId;
    /**
     * 产品能源属性描述符
     */
    private String identity;
    /**
     * 区域id
     */
    private Integer areaId;
    /**
     * 区域名称
     */
    private String areaName;
}
