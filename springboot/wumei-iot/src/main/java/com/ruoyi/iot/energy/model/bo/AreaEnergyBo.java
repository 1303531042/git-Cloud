package com.ruoyi.iot.energy.model.bo;

import lombok.Data;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/10/30
 * @description:
 */
@Data
public class AreaEnergyBo {
    /**
     * 所属子区域列表
     */
    private List<Integer> areaIdList;
    /**
     * 能源类型
     */
    private Integer energyType;
}
