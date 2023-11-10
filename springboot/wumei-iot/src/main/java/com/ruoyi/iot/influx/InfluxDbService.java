package com.ruoyi.iot.influx;

import com.ruoyi.common.influxdb.FluxQueryContainer;
import com.ruoyi.common.influxdb.InfluxProcessor;
import com.ruoyi.common.influxdb.enumeration.FillType;
import com.ruoyi.common.influxdb.enumeration.FluxFunction;
import com.ruoyi.common.influxdb.enumeration.WindowFunction;
import com.ruoyi.common.influxdb.enumeration.WindowTimeIntervalUnit;
import com.ruoyi.common.influxdb.kv.entry.TsKvEntry;
import com.ruoyi.common.influxdb.wrapper.query.FluxQueryWrapper;
import com.ruoyi.common.influxdb.wrapper.query.SimpleQueryWrapper;
import com.ruoyi.common.utils.reflect.ReflectUtils;
import com.ruoyi.iot.energy.service.AreaDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @auther: KUN
 * @date: 2023/8/8
 * @description: influx 服务层实现类
 */
@Slf4j
@Service
public class InfluxDbService extends InfluxProcessor<NormalMeasurement> {
    @Autowired
    private AreaDeviceService areaDeviceService;
    @Autowired
    public InfluxDbService(InfluxMapper influxMapper) {
        super(influxMapper);
    }


    public FluxQueryContainer<NormalMeasurement> queryAllOfWindow(String areaId, String productId, String deviceID, String devField, Date startTime, Date endTime, WindowTimeIntervalUnit windowTimeIntervalUnit, WindowFunction windowFunction, Integer time, FillType fillType, FluxFunction fluxFunction) {
        FluxQueryWrapper<NormalMeasurement> wrapper = new SimpleQueryWrapper<>(ReflectUtils.getClassGenricType(this.getClass()));
        wrapper.andFilter("areaId", areaId)
                .andFilter("productId", productId)
                .andFilter("deviceID", deviceID)
                .andFilter("devField", devField)
                .orColumn("value")
                .rangeTime(startTime, endTime);
        NormalMeasurement normalMeasurement = new NormalMeasurement();
        normalMeasurement.setAreaId(Long.parseLong(areaId));
        normalMeasurement.setProductId(Long.parseLong(productId));
        normalMeasurement.setDeviceID(Long.parseLong(deviceID));
        normalMeasurement.setDevField(devField);
        setWrapperSimpleCacheKeys(wrapper, normalMeasurement);
        return doQueryAllOfWindow(wrapper, startTime, endTime, windowTimeIntervalUnit, windowFunction, time, fluxFunction, fillType);
    }

    public FluxQueryContainer<NormalMeasurement> queryAllOfWindow(String areaId, String productId, String devField, Date startTime, Date endTime, WindowTimeIntervalUnit windowTimeIntervalUnit, WindowFunction windowFunction, Integer time, FluxFunction fluxFunction, FillType fillType) {
        FluxQueryWrapper<NormalMeasurement> wrapper = new SimpleQueryWrapper<>(ReflectUtils.getClassGenricType(this.getClass()));
        wrapper.andFilter("areaId", areaId)
                .andFilter("productId", productId)
                .andFilter("devField", devField)
                .orColumn("value")
                .rangeTime(startTime, endTime);
        NormalMeasurement normalMeasurement = new NormalMeasurement();
        normalMeasurement.setAreaId(Long.parseLong(areaId));
        normalMeasurement.setProductId(Long.parseLong(productId));
        normalMeasurement.setDevField(devField);
        setWrapperSimpleCacheKeys(wrapper, normalMeasurement);
        return doQueryAllOfWindow(wrapper, startTime, endTime, windowTimeIntervalUnit, windowFunction, time, fluxFunction, fillType);

    }

    public FluxQueryContainer<NormalMeasurement> queryAllOfWindow(String deviceID, String devField, Date startTime, Date endTime, WindowTimeIntervalUnit windowTimeIntervalUnit, WindowFunction windowFunction, Integer time, FluxFunction fluxFunction, FillType fillType) {
        FluxQueryWrapper<NormalMeasurement> wrapper = new SimpleQueryWrapper<>(ReflectUtils.getClassGenricType(this.getClass()));
        wrapper.andFilter("deviceID", deviceID)
                .andFilter("devField", devField)
                .orColumn("value")
                .rangeTime(startTime, endTime);
        NormalMeasurement normalMeasurement = new NormalMeasurement();
        normalMeasurement.setDeviceID(Long.parseLong(deviceID));
        normalMeasurement.setDevField(devField);
        setWrapperSimpleCacheKeys(wrapper, normalMeasurement);
        return doQueryAllOfWindow(wrapper, startTime, endTime, windowTimeIntervalUnit, windowFunction, time, fluxFunction, fillType);
    }

    public FluxQueryContainer<NormalMeasurement> queryAllOfWindow(Date startTime, Date endTime,String productId, String devField,  WindowTimeIntervalUnit windowTimeIntervalUnit, WindowFunction windowFunction, Integer time, FluxFunction fluxFunction, FillType fillType) {
        FluxQueryWrapper<NormalMeasurement> wrapper = new SimpleQueryWrapper<>(ReflectUtils.getClassGenricType(this.getClass()));
        wrapper.andFilter("productId", productId)
                .andFilter("devField", devField)
                .orColumn("value")
                .rangeTime(startTime, endTime);
        return doQueryAllOfWindow(wrapper, startTime, endTime, windowTimeIntervalUnit, windowFunction, time, fluxFunction, fillType);


    }
//    public List<TsKvEntry> queryAllOfPoint(String deviceID, String devField, Date startTime, Date endTime, FillType fillType, FluxFunction fluxFunction) {
//
//    }


//    @Override
//    public List<String> getWrapperSimpleCacheKeys(NormalMeasurement measudirection) {
//        AreaDeviceInfluxBo bo = new AreaDeviceInfluxBo();
//        BeanUtils.copyProperties(measudirection, bo);
//        return areaDeviceService.queryAreaDeviceInfluxBoList(bo).stream().map(areaDeviceInfluxBo -> bo.getDeviceID() + "-" + bo.getDevField()).collect(Collectors.toList());
//    }


    @Override
    protected void setWrapperSimpleCacheKeys(FluxQueryWrapper<NormalMeasurement> wrapper, NormalMeasurement measudirection) {
        AreaDeviceInfluxBo bo = new AreaDeviceInfluxBo();
        BeanUtils.copyProperties(measudirection, bo);
        List<String> collect = areaDeviceService.queryAreaDeviceInfluxBoList(bo).stream().map(areaDeviceInfluxBo -> areaDeviceInfluxBo.getDeviceID() + "-" + areaDeviceInfluxBo.getDevField()).collect(Collectors.toList());
        simpleCacheKeysMap.put(wrapper, collect);

    }

    @Override
    public String getMeasudirectionSimpleKey(NormalMeasurement measudirection) {
        return measudirection.getDeviceID() + "-" + measudirection.getDevField();
    }


}
