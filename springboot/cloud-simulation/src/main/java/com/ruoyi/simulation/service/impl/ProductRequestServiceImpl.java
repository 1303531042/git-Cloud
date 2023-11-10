package com.ruoyi.simulation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.simulation.domain.ProductRequest;
import com.ruoyi.simulation.manager.SimulationDeviceManager;
import com.ruoyi.simulation.manager.SimulationInterfaceManager;
import com.ruoyi.simulation.mapper.ProductRequestMapper;
import com.ruoyi.simulation.service.IProductRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/4/17
 * @description: IProductRequestService 服务实现类
 */
@Service
@RequiredArgsConstructor
public class ProductRequestServiceImpl implements IProductRequestService {
    private final ProductRequestMapper productRequestMapper;


    @Override
    public int insertProductRequest(ProductRequest productRequest) {
        int result = productRequestMapper.insert(productRequest);
        if (result == 1) {
            getSimulationInterfaceManager().attach(productRequest);
        }
        return result;
    }

    public SimulationInterfaceManager getSimulationInterfaceManager() {
        return SpringUtils.getBean(SimulationInterfaceManager.class);
    }

    @Override
    public int updateProductProductRequest(ProductRequest productRequest) {
        return productRequestMapper.update(productRequest, new UpdateWrapper<ProductRequest>().eq("id", productRequest.getId()));
    }

    @Override
    public List<ProductRequest> selectProductRequestsByProductId(Integer productId) {
        return productRequestMapper.selectList(new QueryWrapper<ProductRequest>().eq("product_id", productId));
    }

    @Override
    public int removeProductRequest(Integer id) {
        ProductRequest productRequest = productRequestMapper.selectOne(new QueryWrapper<ProductRequest>().eq("id", id));
        getSimulationInterfaceManager().logOut(productRequest);
        return productRequestMapper.delete(new QueryWrapper<ProductRequest>().eq("id", id));
    }

    @Override
    public int removeProductRequestByProductId(Integer productId) {
        List<ProductRequest> productRequests = productRequestMapper.selectList(new QueryWrapper<ProductRequest>().eq("product_id", productId));
        productRequests.forEach(getSimulationInterfaceManager()::logOut);
        return productRequestMapper.delete(new QueryWrapper<ProductRequest>().eq("product_id", productId));

    }

    @Override
    public boolean insertProductRequests(List<ProductRequest> productRequests) {
        boolean b = productRequestMapper.insertBatch(productRequests);
        if (b) {
            productRequests.forEach(getSimulationInterfaceManager()::attach);
        }
        return b;
    }

    @Override
    public List<ProductRequest> selectAllProductRequest() {
        return productRequestMapper.selectList();
    }


}
