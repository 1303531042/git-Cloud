package com.ruoyi.iot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.iot.model.ThingsModelItem.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 设备对象 iot_device
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class DeviceShortOutput
{
    public DeviceShortOutput(){
        this.stringList=new ArrayList<>();
        this.integerList=new ArrayList<>();
        this.decimalList=new ArrayList<>();
        this.enumList=new ArrayList<>();
        this.arrayList=new ArrayList<>();
        this.readOnlyList =new ArrayList<>();
        this.boolList=new ArrayList<>();
    }

    private static final long serialVersionUID = 1L;

    /** 产品分类ID */
    private Long deviceId;

    /** 产品分类名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 产品ID */
    @Excel(name = "产品ID")
    private Long productId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    private String userName;

    /** 租户ID */
    @Excel(name = "租户ID")
    private Long tenantId;

    /** 租户名称 */
    @Excel(name = "租户名称")
    private String tenantName;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String serialNumber;

    /** 固件版本 */
    @Excel(name = "固件版本")
    private BigDecimal firmwareVersion;

    /** 设备状态（1-未激活，2-禁用，3-在线，4-离线） */
    @Excel(name = "设备状态")
    private Integer status;

    /** 设备影子 */
    private Integer isShadow;

    /** wifi信号强度（信号极好4格[-55— 0]，信号好3格[-70— -55]，信号一般2格[-85— -70]，信号差1格[-100— -85]） */
    @Excel(name = "wifi信号强度")
    private Integer rssi;

    @Excel(name = "物模型")
    private String thingsModelValue;

    /** 激活时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "激活时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date activeTime;

    /** 是否自定义位置 **/
    private Integer locationWay;

    /** 图片地址 */
    private String imgUrl;

    /** 是否设备所有者，用于查询 **/
    private Integer isOwner;

    private List<StringModelOutput> stringList;
    private List<IntegerModelOutput> integerList;
    private List<DecimalModelOutput> decimalList;
    private List<EnumModelOutput> enumList;
    private List<ArrayModelOutput> arrayList;
    private List<BoolModelOutput> boolList;
    private List<ReadOnlyModelOutput> readOnlyList;



}
