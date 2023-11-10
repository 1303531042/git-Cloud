package com.ruoyi.iot.energy.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description: 设备所属区域实体
 */
@Data
@TableName("iot_area_device")
public class AreaDevice {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 设备id
     */
    private Integer deviceId;
    /**
     * 产品id
     */
    private Integer productId;
    /**
     * 所属区域id
     */
    private Integer areaId;
    /**
     * 租户id
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer tenantId;
    /**
     * 创建者id
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
