package com.ruoyi.iot.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 设备对象 iot_device
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
@TableName("iot_device")
public class Device
{
    private static final long serialVersionUID = 1L;

    /**
     * 设备id
     */
    @TableId(type = IdType.AUTO)
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
    @TableField(fill = FieldFill.INSERT)
    private Long userId;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    @TableField(fill = FieldFill.INSERT)
    private String userName;

    /** 租户ID */
    @Excel(name = "租户ID")
    @TableField(fill = FieldFill.INSERT)
    private Long tenantId;

    /** 租户名称 */
    @Excel(name = "租户名称")
    @TableField(fill = FieldFill.INSERT)
    private String tenantName;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String serialNumber;


    /** 设备状态（1-未激活，2-禁用，3-在线，4-离线） */
    @Excel(name = "设备状态")
    private Integer status;


    /** 设备影子 */
    private Integer isShadow;

    /** 设备所在地址 */
    @Excel(name = "设备所在地址")
    private String networkAddress;

    /** 设备入网IP */
    @Excel(name = "设备入网IP")
    private String networkIp;

    /** 设备经度 */
    @Excel(name = "设备经度")
    private BigDecimal longitude;

    /** 设备纬度 */
    @Excel(name = "设备纬度")
    private BigDecimal latitude;

    /** 激活时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "激活时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date activeTime;

    @Excel(name = "物模型")
    private String thingsModelValue;

    /** 图片地址 */
    private String imgUrl;

    /** 创建者 */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;

}
