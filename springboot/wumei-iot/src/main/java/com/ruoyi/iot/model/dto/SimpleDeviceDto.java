package com.ruoyi.iot.model.dto;

import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/10/13
 * @description:
 */
@Data
public class SimpleDeviceDto {

    /**
     * 产品id
     */
    private Integer productId;
    /**
     * 区域id
     */
    private Integer areaId;
    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备经度
     */
    private Double longitude;

    /**
     * 设备纬度
     */
    private Double latitude;

}
