package com.ruoyi.iot.influx;

import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/10/26
 * @description:
 */
@Data
public class AreaDeviceInfluxBo {
    /**
     * 区域id
     */
    private String areaId;
    /**
     * 产品id
     */
    private String productId;
    /**
     * 设备id
     */
    private String deviceID;
    /**
     * 属性标识符
     */
    private String devField;
}
