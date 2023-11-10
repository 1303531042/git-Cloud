package com.ruoyi.iot.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 设备用户对象 iot_device_user
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
@TableName("device_user")
public class DeviceUser
{
    private static final long serialVersionUID = 1L;

    /** 固件ID */
    private Long deviceId;

    /** 用户ID */
    private Long userId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    private String userName;

    /** 是否为设备所有者 */
    @Excel(name = "是否为设备所有者")
    private Integer isOwner;

    /** 租户ID */
    private Long tenantId;

    /** 租户名称 */
    private String tenantName;

    /** 手机号码 */
    private String phonenumber;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 创建者 */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
