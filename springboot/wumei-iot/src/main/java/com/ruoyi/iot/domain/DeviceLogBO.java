package com.ruoyi.iot.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @auther: KUN
 * @date: 2023/6/13
 * @description: 设备日志表对象
 */
@Data
@TableName("iot_device_log")
public class DeviceLogBO  {

    /** 设备日志ID */
    @TableId
    private Long logId;

    /**
     *     类型（1=属性上报，2=调用功能，3=事件上报，4=设备升级，5=设备上线，6=设备离线）
     */
    @Excel(name = "类型", readConverterExp = "1==属性上报，2=事件上报，3=调用功能，4=设备升级，5=设备上线，6=设备离线")
    private Integer logType;
    /** 日志值 */
    @Excel(name = "日志值")
    private String logValue;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long deviceId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String serialNumber;

    /** 标识符 */
    @Excel(name = "标识符")
    private String identity;

    /**
     * 标识符 解释
     */
    private String identityName;

    /** 是否监测数据（1=是，0=否） */
    @Excel(name = "是否监测数据", readConverterExp = "1=是，0=否")
    private Integer isMonitor;

    /** 模式 */
    @Excel(name = "模式", readConverterExp = "1=影子模式，2=在线模式,3=其他")
    private Integer mode;

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

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    private String updateBy;

//    /** 更新时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date updateTime;



    /**
     * 备注
     */
    private String remark;

}
