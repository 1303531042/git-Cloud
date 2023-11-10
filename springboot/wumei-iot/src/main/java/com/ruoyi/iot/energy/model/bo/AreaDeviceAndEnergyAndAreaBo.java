package com.ruoyi.iot.energy.model.bo;

import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/10/30
 * @description:
 */
@Data
public class AreaDeviceAndEnergyAndAreaBo {
    /**
     * 能源区域
     */
    private Integer areaId;
    /**
     * 产品id
     */
    private Integer productId;
    /**
     * 能源属性描述符
     */
    private String identity;
}
