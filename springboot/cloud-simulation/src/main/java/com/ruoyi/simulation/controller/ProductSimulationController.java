package com.ruoyi.simulation.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.simulation.domain.ProductSimulation;
import com.ruoyi.simulation.service.ProductSimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @auther: KUN
 * @date: 2023/4/20
 * @description: product 与 simulation 关系类 控制层
 */
@RestController
@RequestMapping("/ProductSimulation")
public class ProductSimulationController extends BaseController {
    @Autowired
    private ProductSimulationService productSimulationService;

    /**
     * 新增一个 simulation 与 product 对应关系
     * @param productSimulation
     * @return
     */
    @PostMapping
    public AjaxResult addProductSimulation(@RequestBody ProductSimulation productSimulation) {
        return toAjax(productSimulationService.insertProductSimulation(productSimulation));
    }
    /**
     * 修改一个 simulation 与 product 对应关系
     * @param productSimulation
     * @return
     */
    @PutMapping
    public AjaxResult updateProductSimulation(@RequestBody ProductSimulation productSimulation) {
        return toAjax(productSimulationService.updateProductSimulation(productSimulation));
    }

    @GetMapping("{productID}")
    public AjaxResult getProductSimulation(@PathVariable("productID")Integer productID) {
        return AjaxResult.success(productSimulationService.queryProductSimulation(productID));
    }
}
