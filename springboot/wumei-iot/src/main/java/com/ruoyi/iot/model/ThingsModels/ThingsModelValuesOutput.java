package com.ruoyi.iot.model.ThingsModels;

import lombok.Data;

/**
 * 设备输入物模型值参数
 *
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class ThingsModelValuesOutput
{
    /** 产品ID **/
    private Long productId;

    private String productName;

    private Long deviceId;

    private String deviceName;

    private int status;

    private int isShadow;

    /** 设备ID **/
    private String serialNumber;

    /** 用户ID */
    private Long userId;

    /** 用户昵称 */
    private String userName;

    /** 租户ID */
    private Long tenantId;

    /** 租户名称 */
    private String tenantName;

    /** 设备物模型值 **/
    private String thingsModelValue;


}
