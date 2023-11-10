package com.ruoyi.iot.energy.model.dto;

import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/10/16
 * @description:
 */
@Data
public class EditSimpleDeviceDto {
    /**
     * 设备id
     */
    private Integer deviceId;
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
