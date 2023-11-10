package com.ruoyi.iot.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.iot.domain.ThingsModel;
import com.ruoyi.iot.model.ImportThingsModelInput;
import com.ruoyi.iot.service.IThingsModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 物模型Controller
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@RestController
@RequestMapping("/iot/model")
@Api(tags="产品物模型")
public class ThingsModelController extends BaseController
{
    @Autowired
    private IThingsModelService thingsModelService;

    /**
     * 查询物模型列表
     */
    @GetMapping("/list")
    @ApiOperation("产品物模型分页列表")
    @SaCheckPermission("iot:device:list")
    public AjaxResult list(ThingsModel thingsModel)
    {
        return AjaxResult.success(thingsModelService.selectThingsModelList(thingsModel));
    }

    /**
     * 获取物模型详细信息
     */
    @GetMapping(value = "/{modelId}")
    @ApiOperation("获取产品物模型详情")
    @SaCheckPermission("iot:device:query")
    public AjaxResult getInfo(@PathVariable("modelId") Long modelId)
    {
        return AjaxResult.success(thingsModelService.selectThingsModelByModelId(modelId));
    }

    /**
     * 新增物模型
     */
    @Log(title = "物模型", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("添加产品物模型")
    @SaCheckPermission("iot:device:add")
    public AjaxResult add(@RequestBody ThingsModel thingsModel)
    {
        int result=thingsModelService.insertThingsModel(thingsModel);
        if(result==1){
            return AjaxResult.success();
        }else if(result==2){
            return AjaxResult.error("产品下的标识符不能重复");
        }else{
            return AjaxResult.error();
        }
    }

    @Log(title = "导入物模型",businessType = BusinessType.INSERT)
    @PostMapping("/import")
    @ApiOperation("导入通用物模型")
    public AjaxResult ImportByTemplateIds(@RequestBody ImportThingsModelInput input){
        int repeatCount=thingsModelService.importByTemplateIds(input);
        if(repeatCount==0){
            return AjaxResult.success("数据导入成功");
        }else{
            return AjaxResult.success(repeatCount+"条数据未导入，标识符重复");
        }
    }

    /**
     * 修改物模型
     */
    @Log(title = "物模型", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改产品物模型")
    @SaCheckPermission("iot:device:edit")
    public AjaxResult edit(@RequestBody ThingsModel thingsModel)
    {
        int result=thingsModelService.updateThingsModel(thingsModel);
        if(result==1){
            return AjaxResult.success();
        }else if(result==2){
            return AjaxResult.error("产品下的标识符不能重复");
        }else{
            return AjaxResult.error();
        }
    }

    /**
     * 删除物模型
     */
    @Log(title = "物模型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{modelIds}")
    @ApiOperation("批量删除产品物模型")
    @SaCheckPermission("iot:device:remove")
    public AjaxResult remove(@PathVariable Long[] modelIds)
    {
        return toAjax(thingsModelService.deleteThingsModelByModelIds(modelIds));
    }

    /**
     * 获取缓存的JSON物模型
     */
    @GetMapping(value = "/cache/{productId}")
    @ApiOperation("获取缓存的JSON物模型")
    @SaCheckPermission("iot:device:query'")
    public AjaxResult getCacheThingsModelByProductId(@PathVariable("productId") Long productId)
    {
        return AjaxResult.success("操作成功",thingsModelService.getCacheThingsModelByProductId(productId));
    }

    /**
     * 获取某个产品的物模型
     */
    @GetMapping(value = "/{productId}")
    public AjaxResult getThingsModelByProductId(@PathVariable("productId") Long productId)
    {
        ThingsModel thingsModel = new ThingsModel();
        thingsModel.setProductId(productId);
        return AjaxResult.success("操作成功",thingsModelService.selectThingsModelList(thingsModel));
    }
}
