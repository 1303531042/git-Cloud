package com.ruoyi.simulation.mapper;

import com.ruoyi.common.mybatisplus.BaseMapperPlus;
import com.ruoyi.simulation.domain.ProductRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther: KUN
 * @date: 2023/4/17
 * @description: ProductRequest 数据层
 */
@Mapper
public interface ProductRequestMapper extends BaseMapperPlus<ProductRequestMapper, ProductRequest, ProductRequest> {

}
