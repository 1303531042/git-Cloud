package com.ruoyi.iot.energy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.iot.energy.domain.EnergyProduct;
import com.ruoyi.iot.energy.mapper.EnergyProductMapper;
import com.ruoyi.iot.energy.model.bo.EnergyProductBo;
import com.ruoyi.iot.energy.model.bo.SelectEnergyProductBo;
import com.ruoyi.iot.energy.service.EnergyProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description: 能源产品服务层实现类
 */
@Service
@RequiredArgsConstructor
public class EnergyProductServiceImpl implements EnergyProductService {

    private final EnergyProductMapper energyProductMapper;

    /**
     * 根据能源类型查询
     * @param energyType
     * @return
     */
    @Override
    public List<EnergyProduct> queryEnergyTypeProducts(int energyType) {
        return energyProductMapper.selectVoList(new QueryWrapper<EnergyProduct>()
                .eq("energy_type", energyType)
        );

    }

    @Override
    public List<EnergyProductBo> queryEnergyProductBoList(SelectEnergyProductBo selectEnergyProductBo) {
        return energyProductMapper.selectEnergyProductBoList(selectEnergyProductBo);
    }


}
