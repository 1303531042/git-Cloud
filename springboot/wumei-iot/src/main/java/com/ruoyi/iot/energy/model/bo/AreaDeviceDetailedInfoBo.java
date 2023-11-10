package com.ruoyi.iot.energy.model.bo;

import lombok.Data;

import java.util.Date;

/**
 * @auther: KUN
 * @date: 2023/10/13
 * @description:
 */
@Data
public class AreaDeviceDetailedInfoBo {
    /**
     * 设备id
     */
    private Integer deviceId;
    /**
     * 产品id
     */
    private Integer productId;
    /**
     * 区域id
     */
    private Integer areaId;
    /**
     * 区域名
     */
    private String areaName;
    /**
     * 设备名
     */
    private String deviceName;
    /**
     * 设备编号
     */
    private String serialNumber;
    /**
     * 产品名
     */
    private String productName;
    /**
     *  设备状态（1-未激活，2-禁用，3-在线，4-离线）
     */
    private Integer status;
    /**
     * 物模型值
     */
    private String thingsModelValue;

    /**
     * 设备经度
     */
    private Double longitude;

    /**
     * 设备纬度
     */
    private Double latitude;
    /**
     * 创建时间
     */
    private Date createTime;
}
