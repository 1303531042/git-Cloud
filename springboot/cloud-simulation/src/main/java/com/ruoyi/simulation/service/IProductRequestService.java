package com.ruoyi.simulation.service;

import com.ruoyi.simulation.domain.ProductRequest;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/4/17
 * @description:
 */
public interface IProductRequestService {
    int insertProductRequest(ProductRequest productRequest);

    int updateProductProductRequest(ProductRequest productRequest);

    List<ProductRequest> selectProductRequestsByProductId(Integer productId);

    int removeProductRequest(Integer id);

    int removeProductRequestByProductId(Integer productId);

    boolean insertProductRequests(List<ProductRequest> productRequests);

    List<ProductRequest> selectAllProductRequest();
}
