package com.ruoyi.iot.energy.mapper;

import com.ruoyi.common.mybatisplus.BaseMapperPlus;
import com.ruoyi.iot.energy.domain.EnergyProduct;
import com.ruoyi.iot.energy.model.bo.EnergyProductBo;
import com.ruoyi.iot.energy.model.bo.SelectEnergyProductBo;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description: 能源产品数据层
 */
public interface EnergyProductMapper extends BaseMapperPlus<EnergyProductMapper, EnergyProduct, EnergyProduct> {

    List<EnergyProductBo> selectEnergyProductBoList(SelectEnergyProductBo selectEnergyProductBo);
}
