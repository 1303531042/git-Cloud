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
 * 通用物模型对象 iot_things_model_template
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
@TableName("iot_things_model_template")
public class ThingsModelTemplate
{
    private static final long serialVersionUID = 1L;

    /** 物模型ID */
    @TableId
    private Long templateId;

    /** 物模型名称 */
    @Excel(name = "物模型名称")
    private String templateName;

    /** 租户ID */
    @Excel(name = "租户ID")
    private Long tenantId;

    /** 租户名称 */
    @Excel(name = "租户名称")
    private String tenantName;

    /** 标识符，产品下唯一 */
    @Excel(name = "标识符")
    private String identifier;

    /** 模型类别（1-属性，2-功能，3-事件） */
    @Excel(name = "模型类别", dictType = "iot_things_type")
    private Integer type;

    /** 数据类型（integer、decimal、string、bool、array、enum） */
    @Excel(name = "数据类型")
    private String datatype;

    /** 数据定义 */
    @Excel(name = "数据定义")
    private String specs;

    /** 是否系统通用（0-否，1-是） */
    @Excel(name = "是否系统通用", dictType = "iot_yes_no")
    private Integer isSys;

    /** 是否首页显示（0-否，1-是） */
    @Excel(name = "是否首页显示", dictType = "iot_yes_no")
    private Integer isTop;

    /** 是否实时监测（0-否，1-是） */
    @Excel(name = "是否实时监测", dictType = "iot_yes_no")
    private Integer isMonitor;

    /**
     * 是否支持图标显示
     */
    private Integer isEcharts;

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

    /** 备注 */
    private String remark;

}
