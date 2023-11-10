package com.ruoyi.simulation.service;

import com.ruoyi.simulation.domain.ProductSimulation;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/4/20
 * @description:  product 与 simulation 关系类 服务层
 */
public interface ProductSimulationService {
    int insertProductSimulation(ProductSimulation productSimulation);

    int updateProductSimulation(ProductSimulation productSimulation);

    List<ProductSimulation> selectAll();

    String queryProductSimulation(Integer productID);
}
