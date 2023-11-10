package com.ruoyi.iot.energy.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.satoken.utils.LoginHelper;
import com.ruoyi.iot.energy.domain.AreaDevice;
import com.ruoyi.iot.energy.domain.EnergyProduct;
import com.ruoyi.iot.energy.model.bo.*;
import com.ruoyi.iot.energy.model.dto.ProportionDto;
import com.ruoyi.iot.energy.model.dto.QueryEnergyTypeDto;
import com.ruoyi.iot.energy.model.dto.QuotaDto;
import com.ruoyi.iot.energy.model.vo.AreaStatisticVo;
import com.ruoyi.iot.energy.model.vo.PieChartVo;
import com.ruoyi.iot.energy.model.vo.QuotaVo;
import com.ruoyi.iot.energy.service.AreaDeviceService;
import com.ruoyi.iot.energy.service.AreaService;
import com.ruoyi.iot.energy.service.EnergyProductService;
import com.ruoyi.iot.energy.service.StatisticsService;
import com.ruoyi.iot.service.IDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.tio.utils.jfinal.P;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @auther: KUN
 * @date: 2023/9/4
 * @description: 能源管理控制层
 */
@Api(tags = "能源管理控制层")
@RestController
@RequestMapping("/energy")
@RequiredArgsConstructor
public class EnergyController {
    private final AreaService areaService;

    private final EnergyProductService energyProductService;

    private final AreaDeviceService areaDeviceService;

    private final StatisticsService statisticsService;

    private final IDeviceService deviceService;


    /**
     * 获取该租户能源区域列表
     */
    @GetMapping("/arealist")
    @ApiOperation("租户能源区域列表")
//    @SaCheckPermission("iot:energy:arealist")
    public AjaxResult areaList() {
        return AjaxResult.success(areaService.queryAreaListByTenantId());
    }

    /**
     * 租户区域能源统计
     */
    @PostMapping("/areastatistic")
    @ApiOperation("租户区域能源统计")
//    @SaCheckPermission("iot:energy:areastatistic")
    public AjaxResult areaStatistic( @RequestBody QueryEnergyTypeDto queryEnergyTypeDto) {
        //获取查询该区域的所有子区域
        List<Integer> areaList = areaService.queryAreaChildPerms(queryEnergyTypeDto.getAreaId());
        if (areaList.isEmpty()) {
            return AjaxResult.success();
        }
        AreaEnergyBo areaEnergyBo = new AreaEnergyBo();
        areaEnergyBo.setAreaIdList(areaList);
        areaEnergyBo.setEnergyType(queryEnergyTypeDto.getEnergyType());
        //找出该租户楼宇能源类型产品id和对应能源属性描述符号
        List<AreaDeviceAndEnergyAndAreaBo>  areaDeviceAndEnergyAndAreaBos = areaDeviceService.queryAreaDeviceAndEnergyAndAreaBoList(areaEnergyBo);
        List<EnergyStatisticTypeBo> energyStatisticTypeBos = areaDeviceAndEnergyAndAreaBos.stream().map(bo -> {
            EnergyStatisticTypeBo energyStatisticTypeBo = new EnergyStatisticTypeBo();
            energyStatisticTypeBo.setProductId(bo.getProductId());
            energyStatisticTypeBo.setIdentity(bo.getIdentity());
            energyStatisticTypeBo.setAreaId(bo.getAreaId());
            energyStatisticTypeBo.setTimeParticles(queryEnergyTypeDto.getTimeParticles());
            energyStatisticTypeBo.setStartTime(queryEnergyTypeDto.getStartTime());
            energyStatisticTypeBo.setStatisticType(queryEnergyTypeDto.getStatisticType());
            return energyStatisticTypeBo;
        }).collect(Collectors.toList());
        Map<Long, BigDecimal> queryValueMap = statisticsService.areaStatistic1(energyStatisticTypeBos);
        StatisticResultBo statisticResultBo = new StatisticResultBo();
        statisticResultBo.setStatisticType(queryEnergyTypeDto.getStatisticType());
        statisticResultBo.setTimeParticles(queryEnergyTypeDto.getTimeParticles());
        statisticResultBo.setStartTime(queryEnergyTypeDto.getStartTime());
        statisticResultBo.setQueryValueMap(queryValueMap);
        AreaStatisticVo areaStatisticVo = statisticsService.handlerResultVo(statisticResultBo);
        return AjaxResult.success(areaStatisticVo);


    }

    /**
     * 能源使用占比
     *
     * @param proportionDto
     * @return
     */
    @PostMapping("/areaproportion")
    public AjaxResult areaProportion(@RequestBody ProportionDto proportionDto) {
        //获取查询该区域的所有子区域
        List<Integer> areaList = areaService.queryAreaChildPerms(proportionDto.getAreaId());
        if (areaList.isEmpty()) {
            return AjaxResult.success();
        }
        AreaEnergyBo areaEnergyBo = new AreaEnergyBo();
        areaEnergyBo.setAreaIdList(areaList);
        areaEnergyBo.setEnergyType(proportionDto.getEnergyType());

        List<AreaDeviceAndEnergyAndAreaBo>  areaDeviceAndEnergyAndAreaBos = areaDeviceService.queryAreaDeviceAndEnergyAndAreaBoList(areaEnergyBo);
        List<ProductStatisticIncreaseLastBo> productStatisticIncreaseLastBos = areaDeviceAndEnergyAndAreaBos.stream().map(bo -> {
            ProductStatisticIncreaseLastBo productStatisticIncreaseLastBo = new ProductStatisticIncreaseLastBo();
            productStatisticIncreaseLastBo.setProductId(bo.getProductId());
            productStatisticIncreaseLastBo.setIdentity(bo.getIdentity());
            productStatisticIncreaseLastBo.setAreaId(bo.getAreaId());
            productStatisticIncreaseLastBo.setTimeParticles(proportionDto.getTimeParticles());
            productStatisticIncreaseLastBo.setStartTime(proportionDto.getStartTime());
            return productStatisticIncreaseLastBo;
        }).collect(Collectors.toList());
        return AjaxResult.success(statisticsService.queryPieChartVosList(productStatisticIncreaseLastBos));

    }

    /**
     * 能源定额
     * @param quotaDto
     * @return
     */
    @PostMapping("/energyquota")
    public AjaxResult energyQuota(@RequestBody QuotaDto quotaDto) {
        //获取查询该区域的所有子区域
        List<Integer> areaList = areaService.queryAreaChildPerms(quotaDto.getAreaId());
        if (areaList.isEmpty()) {
            return AjaxResult.success();
        }
        AreaEnergyBo areaEnergyBo = new AreaEnergyBo();
        areaEnergyBo.setAreaIdList(areaList);
        areaEnergyBo.setEnergyType(quotaDto.getEnergyType());
        List<ProductStatisticIncreaseLastBo> productStatisticIncreaseLastBos = areaDeviceService.queryAreaDeviceAndEnergyAndAreaBoList(areaEnergyBo)
                .stream()
                .map(bo -> {
                    ProductStatisticIncreaseLastBo productStatisticIncreaseLastBo = new ProductStatisticIncreaseLastBo();
                    productStatisticIncreaseLastBo.setProductId(bo.getProductId());
                    productStatisticIncreaseLastBo.setIdentity(bo.getIdentity());
                    productStatisticIncreaseLastBo.setAreaId(bo.getAreaId());
                    productStatisticIncreaseLastBo.setTimeParticles(quotaDto.getTimeParticles());
                    productStatisticIncreaseLastBo.setStartTime(quotaDto.getStartTime());
                    return productStatisticIncreaseLastBo;
                }).collect(Collectors.toList());
        Map<Long, BigDecimal> statisticDeviceData =  statisticsService.areaStatisticDeviceList(productStatisticIncreaseLastBos);
        Map<Long, BigDecimal> quotaData = statisticsService.areaStatisticQuota(statisticDeviceData, quotaDto.getEnergyType());
        QuotaVo quotaVo = statisticsService.handlerResultQuotaVo(statisticDeviceData, quotaData, quotaDto.getTimeParticles(), quotaDto.getStartTime());
        return AjaxResult.success(quotaVo);
    }







}
