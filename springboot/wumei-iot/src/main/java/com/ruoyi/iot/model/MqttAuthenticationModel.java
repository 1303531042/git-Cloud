package com.ruoyi.iot.model;

import lombok.Data;

/**
 * 动作
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class MqttAuthenticationModel
{
    /** Mqtt客户端ID */
    String clientId;

    /** Mqtt用户名 */
    String userName;

    /** Mqtt密码 */
    String password;

    /** 设备编号 */
    String deviceNumber;

    /** 产品ID */
    Long productId;

    /** 设备关联的用户ID */
    Long userId;

    public MqttAuthenticationModel(String clientid,String username,String password,String deviceNumber ,Long productId,Long userId){
        this.clientId=clientid;
        this.userName=username;
        this.password=password;
        this.deviceNumber=deviceNumber;
        this.productId=productId;
        this.userId=userId;
    }
    public MqttAuthenticationModel(String clientid,String username,String password){
        this.clientId=clientid;
        this.userName=username;
        this.password=password;
    }


}
