package com.ruoyi.iot.model;

import lombok.Data;

/**
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class DeviceNumberAndProductId
{
    /** 产品ID，用于自动添加设备 */
    private Long productId;

    /** 设备编号集合 */
    private String deviceNumber;

    public DeviceNumberAndProductId(){}

    public DeviceNumberAndProductId(Long productId, String deviceNumber){
        this.productId=productId;
        this.deviceNumber=deviceNumber;
    }


}
