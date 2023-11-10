package com.ruoyi.iot.energy.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description: 能源产品实体
 */
@Data
@TableName("iot_energy_product")
public class EnergyProduct {
    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 租户id
     */
    private Integer tenantId;
    /**
     * 产品id
     */
    private Integer productId;
    /**
     * 属性描述符
     */
    private String identity;
    /**
     * 能源类型 0-用电 1-用水 2-发电
     */
    private Integer energyType;
    /**
     * 创建者id
     */
    private Integer createBy;
    /**
     * 创建时间
     */
    private Date createTime;
}
