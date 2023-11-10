package com.ruoyi.iot.energy.service;

import com.ruoyi.iot.energy.model.bo.AreaBo;
import com.ruoyi.iot.energy.model.bo.EnergyProductBo;
import com.ruoyi.iot.energy.model.bo.SelectEnergyProductBo;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description: 区域表 服务层
 */
public interface AreaService {


    /**
     * 根据租户id查询他的区域列表
     * @return
     */
    List<AreaBo> queryAreaListByTenantId();

    List<Integer> queryAreaChildPerms(int areaId);


}
