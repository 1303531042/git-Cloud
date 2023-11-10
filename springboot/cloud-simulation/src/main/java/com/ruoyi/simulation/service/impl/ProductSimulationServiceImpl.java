package com.ruoyi.simulation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.simulation.domain.ProductSimulation;
import com.ruoyi.simulation.mapper.ProductSimulationMapper;
import com.ruoyi.simulation.service.ProductSimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @auther: KUN
 * @date: 2023/4/20
 * @description: product 与 simulation 关系类 服务层实现类
 */
@Service
public class ProductSimulationServiceImpl implements ProductSimulationService {
    @Autowired
    private ProductSimulationMapper productSimulationMapper;
    @Override
    public int insertProductSimulation(ProductSimulation productSimulation) {
        return productSimulationMapper.insert(productSimulation);
    }

    @Override
    public int updateProductSimulation(ProductSimulation productSimulation) {
        return productSimulationMapper.update(productSimulation, new UpdateWrapper<ProductSimulation>().eq("product_id", productSimulation.getProductId()));
    }

    @Override
    public List<ProductSimulation> selectAll() {
        return productSimulationMapper.selectList();
    }

    /**
     *  通过产品ID获取他的 simulation name
     * @param productID 产品id
     * @return
     */
    @Override
    public String queryProductSimulation(Integer productID) {

        Optional<ProductSimulation> productSimulationOp = Optional.ofNullable(productSimulationMapper.selectVoOne(new QueryWrapper<ProductSimulation>().eq("product_id", productID)));

        return productSimulationOp.map(ProductSimulation::getSimulationName).orElse("null");


    }
}
