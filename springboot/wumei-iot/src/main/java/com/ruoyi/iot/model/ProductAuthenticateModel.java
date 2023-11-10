package com.ruoyi.iot.model;


import lombok.Data;

@Data
public class ProductAuthenticateModel {
    /** 产品分类ID */
    private Long deviceId;

    /** 产品分类名称 */
    private String deviceName;

    /**设备状态（1-未激活，2-禁用，3-在线，4-离线）**/
    private int status;

    /** 产品ID */
    private Long productId;

    /** 产品名称 */
    private String productName;

    /** 产品状态 1-未发布，2-已发布 */
    private int productStatus;

    /** 是否启用授权码（0-否，1-是） */
    private Integer isAuthorize;

    /** 设备编号 */
    private String serialNumber;

    /** mqtt账号 */
    private String mqttAccount;

    /** mqtt密码 */
    private String mqttPassword;

    /** 产品秘钥 */
    private String mqttSecret;

    private int vertificateMethod;


}
