package com.ruoyi.iot.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 产品对象 iot_product
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
@TableName("iot_product")
public class Product
{
    private static final long serialVersionUID = 1L;

    @TableId
    /** 产品ID */
    private Long productId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 租户ID */
    @Excel(name = "租户ID")
    private Long tenantId;

    /** 租户名称 */
    @Excel(name = "租户名称")
    private String tenantName;

    /** 是否系统通用（0-否，1-是） */
    @Excel(name = "是否系统通用", readConverterExp = "0=-否，1-是")
    private Integer isSys;


    /** 状态（1-未发布，2-已发布，不能修改） */
    @Excel(name = "状态", readConverterExp = "1==未发布，2=已发布，不能修改")
    private Integer status;

    /** 产品分类ID */
    @Excel(name = "产品分类ID")
    private Long categoryId;

    /** 产品分类名称 */
    @Excel(name = "产品分类名称")
    private String categoryName;

    /** 图片地址 */
    private String imgUrl;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 物模型Json **/
    private String thingsModelsJson;


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
