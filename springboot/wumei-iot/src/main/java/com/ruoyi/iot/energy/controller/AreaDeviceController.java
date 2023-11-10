package com.ruoyi.iot.energy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.mybatisplus.page.PageQuery;
import com.ruoyi.common.mybatisplus.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.iot.domain.ThingsModel;
import com.ruoyi.iot.energy.model.bo.*;
import com.ruoyi.iot.energy.model.dto.AreaDeviceListDto;
import com.ruoyi.iot.energy.model.dto.DeviceDashboardDto;
import com.ruoyi.iot.energy.model.vo.AreaDeviceDetailedInfoVo;
import com.ruoyi.iot.energy.model.vo.DeviceDashboardVo;
import com.ruoyi.iot.energy.service.AreaDeviceService;
import com.ruoyi.iot.energy.service.AreaService;
import com.ruoyi.iot.model.ThingsModels.ThingsModelValueItem;
import com.ruoyi.iot.service.IThingsModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @auther: KUN
 * @date: 2023/10/11
 * @description: 设备区域控制层
 */
@RestController
@RequestMapping("/areadevice")
@RequiredArgsConstructor
public class AreaDeviceController {
    private final AreaDeviceService areaDeviceService;
    private final AreaService areaService;
    private final IThingsModelService thingsModelService;

    /**
     * 获取区域设备看板数据
     */
    @GetMapping("/dashboard")
    public TableDataInfo<DeviceDashboardVo> getDeviceDashboard( DeviceDashboardDto deviceDashboardDto,PageQuery pageQuery) {
        //获取该父区域所有可用的子区域
        List<Integer> childPerms = areaService.queryAreaChildPerms(deviceDashboardDto.getAreaId());
        DeviceDashboardBo deviceDashboardBo = new DeviceDashboardBo();
        deviceDashboardBo.setAreaIds(childPerms);
        deviceDashboardBo.setStatus(deviceDashboardDto.getStatus());
        deviceDashboardBo.setProductId(deviceDashboardDto.getProductId());
        deviceDashboardBo.setSerialNumber(deviceDashboardBo.getSerialNumber());
        //获取这些子区域的区域设备信息
        IPage<AreaDeviceInfoBo> areaDeviceInfoPage = areaDeviceService.queryAreaDeviceInfos(deviceDashboardBo, pageQuery);
        List<AreaDeviceInfoBo> areaDeviceInfoBos = areaDeviceInfoPage.getRecords();
        Map<Integer, Map<String, ThingsModel>> productThingsModelMap = new HashMap<>();

        List<DeviceDashboardVo> deviceDashboardVos = areaDeviceInfoBos.stream().map(areaDeviceInfoBo -> {
            DeviceDashboardVo vo = new DeviceDashboardVo();
            List<String> propertyValues = new ArrayList<>();
            vo.setPropertyValues(propertyValues);
            vo.setDeviceName(areaDeviceInfoBo.getDeviceName());
            vo.setStatus(areaDeviceInfoBo.getStatus());
            vo.setProductId(areaDeviceInfoBo.getProductId());
            vo.setSerialNumber(areaDeviceInfoBo.getSerialNumber());
            vo.setProductName(areaDeviceInfoBo.getProductName());
            Map<String, ThingsModel> thingsModelMap = productThingsModelMap.get(deviceDashboardBo.getProductId());
            //该产品的thingsModelMap为空 进行查询
            if (thingsModelMap == null) {
                thingsModelMap = new HashMap<>();
                productThingsModelMap.put(areaDeviceInfoBo.getProductId(), thingsModelMap);
                ThingsModel thingsModel = new ThingsModel();
                thingsModel.setProductId(areaDeviceInfoBo.getProductId().longValue());
                List<ThingsModel> thingsModels = thingsModelService.selectThingsModelList(thingsModel);
                Map<String, ThingsModel> finalThingsModelMap = thingsModelMap;
                thingsModels.forEach(model -> {
                    String identifier = model.getIdentifier();
                    finalThingsModelMap.put(identifier, model);
                });
            }
            //该该产品的属性值转换成对象
            String thingsModelValue = areaDeviceInfoBo.getThingsModelValue();
            List<ThingsModelValueItem> itemList = JSON.parseArray(thingsModelValue, ThingsModelValueItem.class);
            Map<String, ThingsModel> finalThingsModelMap = thingsModelMap;
            itemList.forEach(item -> {
                ThingsModel thingsModel = finalThingsModelMap.get(item.getId());
                String specs = thingsModel.getSpecs();
                JSONObject jsonObject = JSON.parseObject(specs);
                String unit = jsonObject.getString("unit");
                String modelName = thingsModel.getModelName();
                String value = item.getValue();
                if (StringUtils.isNoneBlank(value)) {
                    value= new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).toString();

                }
                String describe = (unit != null) ? modelName + ": " + value + " " + unit : modelName + ": " + value;
                propertyValues.add(describe);
            });
            return vo;

        }).collect(Collectors.toList());
        TableDataInfo<DeviceDashboardVo> result = TableDataInfo.build();
        result.setRows(deviceDashboardVos);
        result.setTotal(areaDeviceInfoPage.getTotal());
        return result;

    }

    /**
     * 获取区域设备 在线离线数
     */
    @GetMapping("status")
    public AjaxResult getAreaDeviceStatus(Integer areaId) {
        //获取该父区域所有可用的子区域
        List<Integer> childPerms = areaService.queryAreaChildPerms(areaId);
        AreaDeviceStatusBo areaDeviceStatusBo = areaDeviceService.queryAreaDeviceStatus(childPerms);
        return AjaxResult.success(areaDeviceStatusBo);
    }

    /**
     * 获取区域设备列表
     * @param areaDeviceListDto
     * @param pageQuery
     */
    @GetMapping("/detailedinfo")
    public TableDataInfo<AreaDeviceDetailedInfoVo> getAreaDeviceList(AreaDeviceListDto areaDeviceListDto, PageQuery pageQuery) {
        AreaDeviceListBo bo = new AreaDeviceListBo();
        List<Integer> childPerms = null;
        if (areaDeviceListDto.getAreaId() != null) {
            //获取该父区域所有可用的子区域
             childPerms = areaService.queryAreaChildPerms(areaDeviceListDto.getAreaId());
        }
        bo.setStatus(areaDeviceListDto.getStatus());
        bo.setProductId(areaDeviceListDto.getProductId());
        bo.setAreaIds(childPerms);
        bo.setSerialNumber(areaDeviceListDto.getSerialNumber());
        bo.setDeviceName(areaDeviceListDto.getDeviceName());
        IPage<AreaDeviceDetailedInfoBo> areaDeviceDetailedInfoIPage = areaDeviceService.queryAreaDeviceDetailedInfoList(bo, pageQuery);
        List<AreaDeviceDetailedInfoBo> records = areaDeviceDetailedInfoIPage.getRecords();
        Map<Integer, Map<String, ThingsModel>> productThingsModelMap = new HashMap<>();

        List<AreaDeviceDetailedInfoVo> deviceDetailedInfoVos = records.stream().map(record -> {
            Map<String, ThingsModel> thingsModelMap = productThingsModelMap.get(record.getProductId());
            List<String> propertyValues = new ArrayList<>();
            //该产品的thingsModelMap为空 进行查询
            if (thingsModelMap == null) {
                thingsModelMap = new HashMap<>();
                productThingsModelMap.put(record.getProductId(), thingsModelMap);
                ThingsModel thingsModel = new ThingsModel();
                thingsModel.setProductId(record.getProductId().longValue());
                List<ThingsModel> thingsModels = thingsModelService.selectThingsModelList(thingsModel);
                Map<String, ThingsModel> finalThingsModelMap = thingsModelMap;
                thingsModels.forEach(model -> {
                    String identifier = model.getIdentifier();
                    finalThingsModelMap.put(identifier, model);
                });
            }
            //该该产品的属性值转换成对象
            String thingsModelValue = record.getThingsModelValue();
            List<ThingsModelValueItem> itemList = JSON.parseArray(thingsModelValue, ThingsModelValueItem.class);
            Map<String, ThingsModel> finalThingsModelMap = thingsModelMap;
            itemList.forEach(item -> {
                ThingsModel thingsModel = finalThingsModelMap.get(item.getId());
                String specs = thingsModel.getSpecs();
                JSONObject jsonObject = JSON.parseObject(specs);
                String unit = jsonObject.getString("unit");
                String modelName = thingsModel.getModelName();
                String value = item.getValue();
                if (StringUtils.isNoneBlank(value)) {
                    value= new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).toString();

                }
                String describe = (unit != null) ? modelName + ": " + value + " " + unit : modelName + ": " + value;
                propertyValues.add(describe);
            });
            AreaDeviceDetailedInfoVo vo = new AreaDeviceDetailedInfoVo();
            vo.setDeviceName(record.getDeviceName());
            vo.setLatitude(record.getLatitude());
            vo.setLongitude(record.getLongitude());
            vo.setProductId(record.getProductId());
            vo.setStatus(record.getStatus());
            if (record.getCreateTime() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                vo.setCreateTime(sdf.format(record.getCreateTime()));
            }
            vo.setSerialNumber(record.getSerialNumber());
            vo.setPropertyValues(propertyValues);
            vo.setProductName(record.getProductName());
            vo.setAreaName(record.getAreaName());
            vo.setAreaId(record.getAreaId());
            vo.setDeviceId(record.getDeviceId());
            return vo;
        }).collect(Collectors.toList());
        TableDataInfo<AreaDeviceDetailedInfoVo> result = TableDataInfo.build();
        result.setRows(deviceDetailedInfoVos);
        result.setTotal(areaDeviceDetailedInfoIPage.getTotal());
        return result;
    }





}
