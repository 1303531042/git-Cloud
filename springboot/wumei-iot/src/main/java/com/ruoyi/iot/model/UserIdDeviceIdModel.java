package com.ruoyi.iot.model;

import lombok.Data;

/**
 * 用户ID和设备ID模型
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class UserIdDeviceIdModel
{
    private Long userId;

    private Long deviceId;

    public UserIdDeviceIdModel(Long userId, Long deviceId){
        this.userId=userId;
        this.deviceId=deviceId;
    }


}
