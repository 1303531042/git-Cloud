package com.ruoyi.iot.energy.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/10/13
 * @description:
 */
@Data
public class AreaDeviceDetailedInfoVo {
    /**
     * 设备id
     */
    private Integer deviceId;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 产品id
     */
    private Integer productId;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 设备编号
     * */
    private String serialNumber;
    /**
     * 区域id
     */
    private Integer areaId;
    /**
     * 区域名
     */
    private String areaName;

    /**
     * 设备状态（1-未激活，2-禁用，3-在线，4-离线）
     */
    private Integer status;

    /**
     * 属性信息
     */
    private List<String> propertyValues;
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
    private String createTime;
}
