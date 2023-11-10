package com.ruoyi.simulation.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther: KUN
 * @date: 2023/4/17
 * @description: ProductRequest实体类
 */
@Data
@TableName("product_request")
public class ProductRequest implements Serializable {
    /**
     *  id
     */
    @TableId
    private Integer id;
    /**
     * 产品id
     */
    @TableField("product_id")
    private Integer productID;
    /**
     * 方法
     */
    @TableField("request_code")
    private Integer requestCode;
    /**
     * thingModeTemplate id
     */
    @TableField("things_model_id")
    private Integer thingsModelID;
    /**
     * thingModeTemplate 类型 1-属性 2-动作 3-事件
     */
    @TableField("things_model_type")
    private Integer thingsModelType;
    /**
     * thingModeTemplate 名字
     */
    @TableField("things_model_name")
    private String thingsModelName;

    /**
     * thingModeTemplate 标识符
     */
    @TableField("identifier")
    private String identifier;
    /**
     * 方法参数json
     */
    @TableField("request_params")
    private String requestParams;


    /**
     * 数据类型 枚举Type Enum code
     */
    @TableField("type_enum")
    private Integer typeEnum;

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


