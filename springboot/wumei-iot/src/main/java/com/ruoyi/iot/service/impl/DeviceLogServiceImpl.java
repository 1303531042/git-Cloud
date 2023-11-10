package com.ruoyi.iot.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.influxdb.enumeration.FillType;
import com.ruoyi.common.influxdb.enumeration.FluxFunction;
import com.ruoyi.common.influxdb.enumeration.WindowFunction;
import com.ruoyi.common.influxdb.enumeration.WindowTimeIntervalUnit;
import com.ruoyi.common.influxdb.kv.entry.TsValue;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.iot.domain.DeviceLog;
import com.ruoyi.iot.domain.DeviceLogBO;
import com.ruoyi.iot.domain.ThingsModel;
import com.ruoyi.iot.influx.InfluxDbService;
import com.ruoyi.iot.mapper.DeviceLogMapper;
import com.ruoyi.iot.model.MonitorModel;
import com.ruoyi.iot.model.ThingsModels.ThingsModelValueItemDto;
import com.ruoyi.iot.model.dto.DeviceEventDto;
import com.ruoyi.iot.model.dto.EchartsDto;
import com.ruoyi.iot.model.dto.HistoryDto;
import com.ruoyi.iot.service.IDeviceLogService;
import com.ruoyi.iot.service.IThingsModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 设备日志Service业务层处理
 * 
 * @author kerwincui
 * @date 2022-01-13
 */
@Service
@RequiredArgsConstructor
public class DeviceLogServiceImpl implements IDeviceLogService 
{
    private final DeviceLogMapper deviceLogMapper;

    private final IThingsModelService thingsModelService;

    private final InfluxDbService influxDbService;



    /**
     * 查询设备日志
     * 
     * @param logId 设备日志主键
     * @return 设备日志
     */
    @Override
    public DeviceLog selectDeviceLogByLogId(Long logId)
    {
        return deviceLogMapper.selectDeviceLogByLogId(logId);
    }

    /**
     * 查询设备事件
     *
     * @param deviceEventDto
     * @return
     */
    @Override
    public List<DeviceLogBO> selectDeviceEvent(DeviceEventDto deviceEventDto) {
        QueryWrapper<DeviceLogBO> wrapper = new QueryWrapper<>();
        wrapper.eq("device_id", deviceEventDto.getDeviceId());
        //排除属性上传和动作调用
        wrapper.notIn(("log_type"), 1, 2);
        wrapper.orderByDesc("create_time");
        if (StringUtils.isNoneBlank(deviceEventDto.getIdentity())) {
            wrapper.eq("identity", deviceEventDto.getIdentity());
        }
        if (deviceEventDto.getStartTime() != null && deviceEventDto.getEndTime() != null) {
            wrapper.between("create_time", deviceEventDto.getStartTime(), deviceEventDto.getEndTime());
        }

       return deviceLogMapper.selectList(wrapper);
    }

    /**
     * 查询设备日志列表
     * 
     * @param deviceLog 设备日志
     * @return 设备日志
     */
    @Override
    public List<DeviceLog> selectDeviceLogList(DeviceLog deviceLog)
    {
//        return logService.selectDeviceLogList(deviceLog);
        return null;
    }

    /**
     * 查询设备监测数据
     *
     * @param deviceLog 设备日志
     * @return 设备日志
     */
    @Override
    public List<MonitorModel> selectMonitorList(DeviceLog deviceLog)
    {
//        return logService.selectMonitorList(deviceLog);
        return null;
    }

    @Override
    public List<EchartsDto> selectHistoryList(HistoryDto historyDto) {

        if (historyDto.getProductId() != null) {
            String thingsModels = thingsModelService.getCacheThingsModelByProductId(historyDto.getProductId());
            JSONObject thingsModelObject = JSONObject.parseObject(thingsModels);
            List<ThingsModelValueItemDto> valueList = new ArrayList<>();
            JSONArray properties = thingsModelObject.getJSONArray("properties");
            valueList.addAll(properties.toJavaList(ThingsModelValueItemDto.class));
            JSONArray functions = thingsModelObject.getJSONArray("functions");
            valueList.addAll(functions.toJavaList(ThingsModelValueItemDto.class));

            List<ThingsModel> thingsModleToEcharts = thingsModelService.getThingsModleToEchartsByProductId(historyDto.getProductId());

            List<EchartsDto> result = new ArrayList<>();
            thingsModleToEcharts.forEach(t->{
                EchartsDto echartsDto = new EchartsDto();
                echartsDto.setName(t.getModelName());
                echartsDto.setIdentifier(t.getIdentifier());
                valueList.forEach(value->{
                    if (Objects.equals(value.getId(), t.getIdentifier())) {
                        echartsDto.setUnit(value.getDataType().getUnit());
                    }
                });
                AtomicReference<List<MonitorModel>> collect = new AtomicReference<>();
                influxDbService
                        .queryAllOfWindow(historyDto.getDeviceID().toString(), t.getIdentifier(), historyDto.getBeginTime(), historyDto.getEndTime(), WindowTimeIntervalUnit.HOUR, WindowFunction.LAST, 1, FluxFunction.NULL_FUNCTION, FillType.IGNORE_VALUE).getResultMap()
                        .values()
                        .stream()
                        .findFirst()
                        .ifPresent(kvEntries->{
                           collect.set(kvEntries
                                   .stream()
                                   .map(tsKvEntry -> {
                                       TsValue tsValue = tsKvEntry.toTsValue();
                                       MonitorModel monitorModel = new MonitorModel();
                                       monitorModel.setTime(new Date(tsValue.getTs()));
                                       monitorModel.setValue(tsValue.getValue());
                                       return monitorModel;
                                   }).collect(Collectors.toList()));
                        });

                echartsDto.setMonitorModels(collect.get());
                result.add(echartsDto);
            });
            return result;
        }
        return null;
    }


    /**
     * 新增设备日志
     * 
     * @return 结果
     */
    @Override
    public int insertDeviceLog(DeviceLogBO deviceLogBO)
    {
        deviceLogMapper.insert(deviceLogBO);
        return 1;
    }

    @Override
    public void insertDeviceLogs(List<DeviceLogBO> deviceLogBOs) {
        deviceLogMapper.insertBatch(deviceLogBOs);
    }

    @Override
    public int insertDeviceLogList(List<DeviceLogBO> deviceLogBOS) {
        deviceLogMapper.insertBatch(deviceLogBOS);
        return 1;
    }

    /**
     * 修改设备日志
     * 
     * @param deviceLog 设备日志
     * @return 结果
     */
    @Override
    public int updateDeviceLog(DeviceLog deviceLog)
    {
        return deviceLogMapper.updateDeviceLog(deviceLog);
    }

    /**
     * 批量删除设备日志
     * 
     * @param logIds 需要删除的设备日志主键
     * @return 结果
     */
    @Override
    public int deleteDeviceLogByLogIds(Long[] logIds)
    {
        return deviceLogMapper.deleteDeviceLogByLogIds(logIds);
    }

    /**
     * 根据设备Ids批量删除设备日志
     *
     * @param deviceNumber 需要删除数据的设备Ids
     * @return 结果
     */
    @Override
    public int deleteDeviceLogByDeviceNumber(String deviceNumber)
    {
        return deviceLogMapper.deleteDeviceLogByDeviceNumber(deviceNumber);
    }

    /**
     * 删除设备日志信息
     * 
     * @param logId 设备日志主键
     * @return 结果
     */
    @Override
    public int deleteDeviceLogByLogId(Long logId)
    {
        return deviceLogMapper.deleteDeviceLogByLogId(logId);
    }
}
