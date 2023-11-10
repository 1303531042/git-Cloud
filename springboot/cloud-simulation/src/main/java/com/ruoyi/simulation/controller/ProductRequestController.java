package com.ruoyi.simulation.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.simulation.domain.ProductRequest;
import com.ruoyi.simulation.service.IProductRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/4/17
 * @description: 产品 与requestEnum对应
 */
@RestController
@RequestMapping("/iot/productRequest")
@RequiredArgsConstructor
public class ProductRequestController extends BaseController {

    private final IProductRequestService productRequestService;

    /**
     * 新增一个 productRequest
     */
    @PostMapping
    public AjaxResult addProductRequest(@RequestBody ProductRequest productRequest) {
        return AjaxResult.success(productRequestService.insertProductRequest(productRequest));
    }
    /**
     * 新增 productRequest 列表
     */
    @PostMapping("/list")
    public AjaxResult addProductRequests(@RequestBody List<ProductRequest> productRequests) {
        return AjaxResult.success(productRequestService.insertProductRequests(productRequests));
    }

    /**
     * 根据id更新productRequest
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ProductRequest productRequest) {
        return toAjax(productRequestService.updateProductProductRequest(productRequest));
    }


    /**
     * 获取一个产品的productRequest列表
     */
    @GetMapping(value = "/{productId}")
    public AjaxResult getInfo(@PathVariable("productId") Integer productId)
    {
        return AjaxResult.success(productRequestService.selectProductRequestsByProductId(productId));
    }

    /**
     *  通过ID删除产品的一个 product request
     */
    @DeleteMapping(value = "/{id}")
    public AjaxResult removeProductRequest(@PathVariable("id") Integer id) {
        return AjaxResult.success(productRequestService.removeProductRequest(id));
    }

    /**
     *  通过ID删除产品的一个 product request
     */
    @DeleteMapping(value = "/{productId}")
    public AjaxResult removeProductRequestByProductId(@PathVariable("productId") Integer productId) {
        return AjaxResult.success(productRequestService.removeProductRequestByProductId(productId));
    }

}
