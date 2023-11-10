package com.ruoyi.iot.energy.model.bo;

import lombok.Data;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/10/23
 * @description:
 */
@Data
public class SelectEnergyProductBo {
    /**
     * 能源类型
     */
    private Integer energyType;
    /**
     * 产品id
     */
    private Integer productId;
    /**
     * 子区域列表
     */
    private List<Integer> areaList;
    /**
     * 租户id
     */
    private Integer tenantId;



}
