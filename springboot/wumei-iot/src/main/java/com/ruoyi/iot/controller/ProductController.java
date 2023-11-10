package com.ruoyi.iot.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.iot.domain.Product;
import com.ruoyi.iot.model.ChangeProductStatusModel;
import com.ruoyi.iot.model.vo.product.IdAndName;
import com.ruoyi.iot.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品Controller
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Api(tags = "产品管理")
@RestController
@RequestMapping("/iot/product")
public class ProductController extends BaseController
{
    @Autowired
    private IProductService productService;

//    /**
//     * 查询产品列表
//     */
//    @GetMapping("/list")
//    @ApiOperation("产品分页列表")
//    public IPage<Product> list(@PathVariable(name = "page")Long page,@PathVariable(name = "limit") Long limit, Product product) {
//
//
//        return productService.selectProductList(page, limit, product);
//    }

    @GetMapping("/list")
    @ApiOperation("产品分页列表")
    public AjaxResult list(Product product) {


        return AjaxResult.success(productService.selectProductList(product));
    }

    /**
     * 查询产品简短列表
     */
    @GetMapping("/shortList")
    @ApiOperation("产品简短列表")
    @SaCheckPermission("iot:product:list")
    public AjaxResult shortList(Product product)
    {

        return AjaxResult.success(productService.selectProductShortList(product));
    }

//    /**
//     * 导出产品列表
//     */
//    @Log(title = "产品", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    @ApiOperation("导出产品")
//    @SaCheckPermission("iot:product:export")
//    public void export(HttpServletResponse response, Product product)
//    {
//        List<Product> list = productService.selectProductList(Long page, Long limit, );
//        ExcelUtil<Product> util = new ExcelUtil<Product>(Product.class);
//        util.exportExcel(response, list, "产品数据");
//    }

    /**
     * 获取产品详细信息
     */
    @GetMapping(value = "/{productId}")
    @ApiOperation("获取产品详情")
    @SaCheckPermission("iot:product:query")
    public AjaxResult getInfo(@PathVariable("productId") Long productId)
    {
        return AjaxResult.success(productService.selectProductByProductId(productId));
    }

    /**
     * 新增产品
     */
    @Log(title = "产品", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("添加产品")
    @SaCheckPermission("iot:product:add")
    public AjaxResult add(@RequestBody Product product)
    {
        return AjaxResult.success(productService.insertProduct(product));
    }

    /**
     * 修改产品
     */
    @Log(title = "产品", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改产品")
    @SaCheckPermission("iot:product:edit")
    public AjaxResult edit(@RequestBody Product product)
    {
        return toAjax(productService.updateProduct(product));
    }

    /**
     * 发布产品
     */
    @Log(title = "更新产品状态", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    @ApiOperation("更新产品状态")
    @SaCheckPermission("iot:product:edit")
    public AjaxResult changeProductStatus(@RequestBody ChangeProductStatusModel model)
    {
        return productService.changeProductStatus(model);
    }

    /**
     * 删除产品
     */
    @Log(title = "产品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{productIds}")
    @ApiOperation("批量删除产品")
    @SaCheckPermission("iot:product:remove")
    public AjaxResult remove(@PathVariable Long[] productIds)
    {
        return productService.deleteProductByProductIds(productIds);
    }

    @GetMapping("/thingsModel/{productId}")
    public AjaxResult getProductThingModel(@PathVariable("productId") Integer productId) {
        Product product = productService.selectProductByProductId(Long.valueOf(productId));
        if (product != null) {
            return AjaxResult.success(product.getThingsModelsJson());
        }
        return AjaxResult.success("");
    }


}
