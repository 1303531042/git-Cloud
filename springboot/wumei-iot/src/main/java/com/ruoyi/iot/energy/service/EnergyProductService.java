package com.ruoyi.iot.energy.service;

import com.ruoyi.iot.energy.domain.EnergyProduct;
import com.ruoyi.iot.energy.model.bo.EnergyProductBo;
import com.ruoyi.iot.energy.model.bo.SelectEnergyProductBo;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description: 能源产品服务层
 */
public interface EnergyProductService {
    List<EnergyProduct> queryEnergyTypeProducts(int energyType);
    List<EnergyProductBo> queryEnergyProductBoList(SelectEnergyProductBo selectEnergyProductBo);

}
