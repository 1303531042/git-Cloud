package com.ruoyi.iot.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 设备告警对象 iot_alert
 * 
 * @author kerwincui
 * @date 2022-01-13
 */
@Data
@TableName("iot_alert")
public class Alert
{
    private static final long serialVersionUID = 1L;

    /** 告警ID */
    @TableId
    private Long alertId;

    /** 告警名称 */
    @Excel(name = "告警名称")
    private String alertName;

    /** 告警级别（1=提醒通知，2=轻微问题，3=严重警告） */
    @Excel(name = "告警级别", readConverterExp = "1==提醒通知，2=轻微问题，3=严重警告")
    private Long alertLevel;

    /** 产品ID */
    @Excel(name = "产品ID")
    private Long productId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 触发器 */
    @Excel(name = "触发器")
    private String triggers;

    /** 执行动作 */
    @Excel(name = "执行动作")
    private String actions;

    /** 告警状态 （1-启动，2-停止）**/
    private Integer status;

    /** 租户ID */
    private Long tenantId;

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
