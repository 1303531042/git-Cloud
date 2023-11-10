package com.ruoyi.iot.model;

import lombok.Data;

/**
 * 设备分组对象 iot_device_group
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class DeviceGroupInput
{
    private static final long serialVersionUID = 1L;

    /** 分组ID */
    private Long groupId;

    /** 设备ID */
    private Long[] deviceIds;




}
