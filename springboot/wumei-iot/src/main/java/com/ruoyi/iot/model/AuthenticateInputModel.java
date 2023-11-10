package com.ruoyi.iot.model;

import lombok.Data;

/**
 * 动作
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class AuthenticateInputModel
{
    /** 设备编号 */
    private String serialNumber;

    /** 产品ID */
    private Long productId;

    public AuthenticateInputModel(String serialNumber,Long productId){
        this.serialNumber=serialNumber;
        this.productId=productId;
    }


}
