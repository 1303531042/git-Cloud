package com.ruoyi.iot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 设备对象 iot_device
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class DeviceAllShortOutput
{
    private static final long serialVersionUID = 1L;

    /** 产品分类ID */
    private Long deviceId;

    /** 产品分类名称 */
    private String deviceName;

    /** 产品名称 */
    private String productName;

    /** 用户昵称 */
    private String userName;

    /** 设备编号 */
    private String serialNumber;

    /** 固件版本 */
    private BigDecimal firmwareVersion;

    /** 设备状态（1-未激活，2-禁用，3-在线，4-离线） */
    private Integer status;

    /** 设备影子 */
    private Integer isShadow;

    /** wifi信号强度（信号极好4格[-55— 0]，信号好3格[-70— -55]，信号一般2格[-85— -70]，信号差1格[-100— -85]） */
    private Integer rssi;

    /** 激活时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date activeTime;

    /** 是否自定义位置 **/
    private Integer locationWay;

    /** 设备地址 */
    private String networkAddress;

    /** 经度 */
    private BigDecimal longitude;

    /** 纬度 */
    private BigDecimal latitude;

    /** 是否设备所有者，用于查询 **/
    private Integer isOwner;



}
