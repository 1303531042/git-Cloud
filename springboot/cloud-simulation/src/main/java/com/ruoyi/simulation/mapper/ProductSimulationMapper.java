package com.ruoyi.simulation.mapper;

import com.ruoyi.common.mybatisplus.BaseMapperPlus;
import com.ruoyi.simulation.domain.ProductSimulation;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther: KUN
 * @date: 2023/4/24
 * @description: 产品和simulation 映射表 数据层
 */
@Mapper
public interface ProductSimulationMapper extends BaseMapperPlus<ProductSimulationMapper, ProductSimulation, ProductSimulation> {

}
