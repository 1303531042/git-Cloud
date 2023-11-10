package com.ruoyi.iot.energy.service;

import com.ruoyi.common.influxdb.kv.entry.TsValue;
import com.ruoyi.iot.energy.model.bo.*;
import com.ruoyi.iot.energy.model.vo.AreaStatisticVo;
import com.ruoyi.iot.energy.model.vo.PieChartVo;
import com.ruoyi.iot.energy.model.vo.QuotaVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description: 统计服务层
 */
public interface StatisticsService {
    List<List<TsValue>> deviceStatistic(QueryEnergyBo queryEnergyBo);

    Map<Long, BigDecimal> areaStatistic(List<QueryEnergyBo> queryEnergyBos);
    Map<Long, BigDecimal> areaStatistic1(List<EnergyStatisticTypeBo> energyStatisticTypeBos);

    Double deviceStatisticIncreaseLast(DeviceStatisticIncreaseLastBo bo);

    AreaStatisticVo handlerResultVo(StatisticResultBo statisticResultBo);

    Map<Long, BigDecimal> areaStatisticDevices(List<StatisticDeviceBo> StatisticDeviceBos);

    Map<Long, BigDecimal> areaStatisticDeviceList(List<ProductStatisticIncreaseLastBo> productStatisticIncreaseLastBoList);


    Map<Long, BigDecimal> areaStatisticQuota(Map<Long, BigDecimal> statisticDeviceData, int energyType);


    QuotaVo handlerResultQuotaVo(Map<Long, BigDecimal> statisticDeviceData, Map<Long, BigDecimal> quotaData, Integer timeParticles, Date startTime);

    List<PieChartVo> queryPieChartVosList(List<ProductStatisticIncreaseLastBo> productStatisticIncreaseLastBos);
}
