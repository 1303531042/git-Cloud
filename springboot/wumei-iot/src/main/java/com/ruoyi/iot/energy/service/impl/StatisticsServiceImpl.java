package com.ruoyi.iot.energy.service.impl;

import com.ruoyi.common.influxdb.FluxQueryContainer;
import com.ruoyi.common.influxdb.enumeration.FillType;
import com.ruoyi.common.influxdb.enumeration.FluxFunction;
import com.ruoyi.common.influxdb.enumeration.WindowFunction;
import com.ruoyi.common.influxdb.enumeration.WindowTimeIntervalUnit;
import com.ruoyi.common.influxdb.kv.entry.TsKvEntry;
import com.ruoyi.common.influxdb.kv.entry.TsValue;
import com.ruoyi.iot.energy.model.bo.*;
import com.ruoyi.iot.energy.model.vo.AreaStatisticVo;
import com.ruoyi.iot.energy.model.vo.EchartsVo;
import com.ruoyi.iot.energy.model.vo.PieChartVo;
import com.ruoyi.iot.energy.model.vo.QuotaVo;
import com.ruoyi.iot.energy.service.StatisticsService;
import com.ruoyi.iot.influx.InfluxDbService;
import com.ruoyi.iot.influx.NormalMeasurement;
import com.ruoyi.iot.service.IDeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description: 统计服务层实现类
 */
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final InfluxDbService influxDbService;
    private final IDeviceService deviceService;


    @Override
    public Double deviceStatisticIncreaseLast(DeviceStatisticIncreaseLastBo bo) {
        Date endTime = handlerCurrentEndTime(bo.getTimeParticles(), bo.getStartTime());
        InfluxWindowBo influxWindowBo = handlerTimeParticles(bo.getTimeParticles());
        AtomicReference<List<TsKvEntry>> last = new AtomicReference<>();
        influxDbService.queryAllOfWindow(bo.getDeviceId(), bo.getIdentity(), bo.getStartTime(), endTime, influxWindowBo.getUnit(), influxWindowBo.getFunction(), influxWindowBo.getTime(), FluxFunction.LAST_FUNCTION, FillType.NULL_VALUE)
                .getResultMap().values().stream().findFirst().ifPresent(last::set);
        AtomicReference<List<TsKvEntry>> first = new AtomicReference<>();
        influxDbService.queryAllOfWindow( bo.getDeviceId(), bo.getIdentity(), bo.getStartTime(), endTime,influxWindowBo.getUnit(), influxWindowBo.getFunction(), influxWindowBo.getTime(),FluxFunction.FIRST_FUNCTION,FillType.NULL_VALUE)
                .getResultMap().values().stream().findFirst().ifPresent(first::set);
        double result = 0.0;
        if ((last.get() != null && last.get().size() > 0) && (first.get() != null && first.get().size() > 0)) {
            TsKvEntry lastEntry = last.get().get(0);
            TsKvEntry firstEntry = first.get().get(0);
            if (lastEntry.getValue() != null && firstEntry.getValue() != null) {
                Double lastDouble = lastEntry.getDoubleValue().get();
                Double firstDouble = firstEntry.getDoubleValue().get();
                result = lastDouble - firstDouble;
                BigDecimal bigDecimal = new BigDecimal(result);
                result = bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
        }
        if (result < 0) {
            result = 0.0;
        }
        return result;


    }

    /**
     * 创建同比环比返回对象
     * @param statisticResultBo
     * @return
     */
    @Override
    public AreaStatisticVo handlerResultVo(StatisticResultBo statisticResultBo) {
        AreaStatisticVo areaStatisticVo = new AreaStatisticVo();
        EchartsVo echartsVo1 = new EchartsVo();
        EchartsVo echartsVo2 = new EchartsVo();
        List<EchartsVo> echartsVos = new ArrayList<>();
        echartsVos.add(echartsVo1);
        echartsVos.add(echartsVo2);
        areaStatisticVo.setEchartsVos(echartsVos);

        List<Date> currentDates = new ArrayList<>();
        Date currentDate = statisticResultBo.getStartTime();
        switch (statisticResultBo.getTimeParticles()) {
                //日
            case 0:
                currentDates.add(currentDate);
                for (int i = 0; i < 23; i++) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);
                    calendar.add(Calendar.HOUR_OF_DAY, 1);
                    currentDate = calendar.getTime();
                    currentDates.add(currentDate);
                }
                break;
                //月
            case 1:
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(statisticResultBo.getStartTime());
                int currentMonth = calendar.get(Calendar.MONTH);
                int startMonth = calendar.get(Calendar.MONTH);
                while (startMonth == currentMonth) {
                    currentDate = calendar.getTime();
                    currentDates.add(currentDate);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    currentMonth=calendar.get(Calendar.MONTH);
                }
                break;
                //年
            case 2:
                currentDates.add(currentDate);
                for (int i = 0; i < 11; i++) {
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(currentDate);
                    calendar1.add(Calendar.MONTH, 1);
                    currentDate = calendar1.getTime();
                    currentDates.add(currentDate);
                }
                break;
        }
        List<Date> comparedDates = currentDates.stream().map(date -> getStatisticDate(statisticResultBo.getStatisticType(), statisticResultBo.getTimeParticles(), date)).collect(Collectors.toList());

        SimpleDateFormat dateFormat = null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat format3 = new SimpleDateFormat("yyyy");
        switch (statisticResultBo.getTimeParticles()) {
            case 0:
                echartsVo1.setName(format1.format(currentDates.get(0)));
                echartsVo2.setName(format1.format(comparedDates.get(0)));
                areaStatisticVo.setXUnit("时");
                dateFormat = new SimpleDateFormat("HH");
                break;
            case 1:
                echartsVo1.setName(format2.format(currentDates.get(0)));
                echartsVo2.setName(format2.format(comparedDates.get(0)));
                areaStatisticVo.setXUnit("日");
                dateFormat = new SimpleDateFormat("dd");
                break;
            case 2:
                echartsVo1.setName(format3.format(currentDates.get(0)));
                echartsVo2.setName(format3.format(comparedDates.get(0)));
                areaStatisticVo.setXUnit("月");
                dateFormat = new SimpleDateFormat("MM");
                break;
        }
        SimpleDateFormat finalDateFormat = dateFormat;
        areaStatisticVo.setXAxis(currentDates.stream().map(date -> finalDateFormat.format(date)).collect(Collectors.toList()));

        //设置y1data
        echartsVo1.setYDataList(currentDates.stream().map(date -> {
            BigDecimal bigDecimal = statisticResultBo.getQueryValueMap().get(date.getTime());
            if (bigDecimal == null||bigDecimal.doubleValue()<0) {
                return 0.0;
            }
            return bigDecimal.doubleValue();
        }).collect(Collectors.toList()));
        //设置y2data
        echartsVo2.setYDataList(comparedDates.stream().map(date -> {
            BigDecimal bigDecimal = statisticResultBo.getQueryValueMap().get(date.getTime());
            if (bigDecimal == null||bigDecimal.doubleValue()<0) {
                return 0.0;
            }
            return bigDecimal.doubleValue();
        }).collect(Collectors.toList()));

        return areaStatisticVo;
    }

    @Override
    public Map<Long, BigDecimal> areaStatisticDevices(List<StatisticDeviceBo> StatisticDeviceBos) {
        Map<Long, BigDecimal> map1 = new LinkedHashMap<>();

        List<List<TsValue>> collect = StatisticDeviceBos.stream().map(StatisticDeviceBo -> {
            InfluxWindowBo influxWindowBo = handlerTimeParticles(StatisticDeviceBo.getTimeParticles());
            Date endTime = handlerCurrentEndTime(StatisticDeviceBo.getTimeParticles(), StatisticDeviceBo.getStartTime());
            return influxDbService.queryAllOfWindow(StatisticDeviceBo.getDeviceId().toString(), StatisticDeviceBo.getIdentity(),
                            StatisticDeviceBo.getStartTime(), endTime, influxWindowBo.getUnit(), influxWindowBo.getFunction(), influxWindowBo.getTime(), FluxFunction.NULL_FUNCTION, FillType.IGNORE_VALUE).getResultMap().values().stream().findFirst().orElse(new ArrayList<TsKvEntry>())
                    .stream().map(TsKvEntry::toTsValue).collect(Collectors.toList());
        }).collect(Collectors.toList());

        collect.forEach(tsValues -> {
            for (int i = 0; i < tsValues.size() - 1; i++) {
                TsValue current = tsValues.get(i);
                TsValue next = tsValues.get(i + 1);

                BigDecimal currentValue = new BigDecimal(String.valueOf(current.getValue()));
                BigDecimal nextValue = new BigDecimal(String.valueOf(next.getValue()));

                BigDecimal difference = nextValue.subtract(currentValue).setScale(2, RoundingMode.HALF_UP);

                current.setValue(difference.toString());
            }
            if (tsValues.size() != 0) {
                tsValues.remove(tsValues.size() - 1);
            }
        });

        collect.forEach(tsValues -> {
            calculateSum(tsValues, map1);
        });


        return map1;

    }

    @Override
    public Map<Long, BigDecimal> areaStatisticDeviceList(List<ProductStatisticIncreaseLastBo> productStatisticIncreaseLastBoList) {
        Map<Long, BigDecimal> map = new LinkedHashMap<>();

        //查询出符合条件的设备值
        List<List<TsValue>> mergedList = productStatisticIncreaseLastBoList.stream()
                .map(bo -> {
                    InfluxWindowBo influxWindowBo = handlerTimeParticles(bo.getTimeParticles());
                    Date endTime = handlerCurrentEndTime(bo.getTimeParticles(), bo.getStartTime());
                    return influxDbService.queryAllOfWindow(bo.getAreaId().toString(), bo.getProductId().toString(), bo.getIdentity(), bo.getStartTime(), endTime, influxWindowBo.getUnit(), influxWindowBo.getFunction(), influxWindowBo.getTime(), FluxFunction.NULL_FUNCTION, FillType.IGNORE_VALUE).getResultMap().values().
                            stream().map(tsKvEntries -> tsKvEntries.stream().map(TsKvEntry::toTsValue).collect(Collectors.toList())).collect(Collectors.toList());
                })
                .flatMap(Collection::stream) // Flatten the inner collections
                .collect(Collectors.toList());
        mergedList.forEach(tsValueList -> {
            for (int i = 0; i < tsValueList.size() - 1; i++) {
                TsValue current = tsValueList.get(i);
                TsValue next = tsValueList.get(i+1);

                BigDecimal currentValue = new BigDecimal(String.valueOf(current.getValue()));
                BigDecimal nextValue = new BigDecimal(String.valueOf(next.getValue()));

                BigDecimal difference = nextValue.subtract(currentValue).setScale(2, RoundingMode.HALF_UP);

                current.setValue(difference.toString());
            }
            if (tsValueList.size() != 0) {
                tsValueList.remove(tsValueList.size() - 1);
            }
            calculateSum(tsValueList,map);
        });


        return map;
    }


    @Override
    public List<List<TsValue>> deviceStatistic(QueryEnergyBo queryEnergyBo) {
        InfluxWindowBo influxWindowBo = handlerTimeParticles(queryEnergyBo.getTimeParticles());
        List<InfluxTimeBo> influxTimeBos = handlerStatisticsTime(queryEnergyBo.getTimeParticles(), queryEnergyBo.getStatisticType(), queryEnergyBo.getStartTime());


        List<List<TsValue>> collect = influxTimeBos.stream().map((influxTimeBo -> influxDbService.queryAllOfWindow(queryEnergyBo.getDeviceId().toString(), queryEnergyBo.getIdentity(), influxTimeBo.getStartTime(), influxTimeBo.getEndTime(), influxWindowBo.getUnit(), influxWindowBo.getFunction(), influxWindowBo.getTime(), FluxFunction.NULL_FUNCTION, FillType.IGNORE_VALUE)
                .getResultMap().values().stream().findFirst().orElse(new ArrayList<TsKvEntry>()).stream().map(TsKvEntry::toTsValue).collect(Collectors.toList()))).collect(Collectors.toList());

        collect.forEach(tsValues -> {
            for (int i = 0; i < tsValues.size() - 1; i++) {
                TsValue current = tsValues.get(i);
                TsValue next = tsValues.get(i + 1);

                BigDecimal currentValue = new BigDecimal(String.valueOf(current.getValue()));
                BigDecimal nextValue = new BigDecimal(String.valueOf(next.getValue()));

                BigDecimal difference = nextValue.subtract(currentValue).setScale(2, RoundingMode.HALF_UP);

                current.setValue(difference.toString());
            }
            if (tsValues.size() != 0) {
                tsValues.remove(tsValues.size() - 1);
            }
        });
        return collect;


    }
    public List<List<TsValue>> deviceStatistic1(EnergyStatisticTypeBo energyStatisticTypeBo) {
        InfluxWindowBo influxWindowBo = handlerTimeParticles(energyStatisticTypeBo.getTimeParticles());
        List<InfluxTimeBo> influxTimeBos = handlerStatisticsTime(energyStatisticTypeBo.getTimeParticles(), energyStatisticTypeBo.getStatisticType(), energyStatisticTypeBo.getStartTime());
        List<List<TsValue>> statisticList = new ArrayList<>();
        List<List<List<TsValue>>> collect = influxTimeBos.stream()
                .map(influxTimeBo -> {
                    return influxDbService.queryAllOfWindow(
                                    energyStatisticTypeBo.getAreaId().toString(),
                                    energyStatisticTypeBo.getProductId().toString(),
                                    energyStatisticTypeBo.getIdentity(),
                                    influxTimeBo.getStartTime(),
                                    influxTimeBo.getEndTime(),
                                    influxWindowBo.getUnit(),
                                    influxWindowBo.getFunction(),
                                    influxWindowBo.getTime(),
                                    FluxFunction.NULL_FUNCTION,
                                    FillType.IGNORE_VALUE
                            )
                            .getResultMap()
                            .values()
                            .stream()
                            .map(tsKvEntries -> tsKvEntries.stream()
                                    .map(TsKvEntry::toTsValue)
                                    .collect(Collectors.toList())
                            )
                            .collect(Collectors.toList());
                }).collect(Collectors.toList());
        collect.forEach(statisticList::addAll);
        statisticList.forEach(tsValues -> {
            for (int i = 0; i < tsValues.size() - 1; i++) {
                TsValue current = tsValues.get(i);
                TsValue next = tsValues.get(i + 1);

                BigDecimal currentValue = new BigDecimal(String.valueOf(current.getValue()));
                BigDecimal nextValue = new BigDecimal(String.valueOf(next.getValue()));

                BigDecimal difference = nextValue.subtract(currentValue).setScale(2, RoundingMode.HALF_UP);

                current.setValue(difference.toString());
            }
            if (tsValues.size() != 0) {
                tsValues.remove(tsValues.size() - 1);
            }
        });
        return statisticList;
    }

    @Override
    public Map<Long, BigDecimal> areaStatistic(List<QueryEnergyBo> queryEnergyBos) {
        Map<Long, BigDecimal> map1 = new LinkedHashMap<>();
        Map<Long, BigDecimal> map2 = new LinkedHashMap<>();
        List<List<List<TsValue>>> collect = queryEnergyBos.stream().map(this::deviceStatistic).collect(Collectors.toList());
        collect.forEach(a -> {
            List<TsValue> tsValues1 = a.get(0);
            calculateSum(tsValues1, map1);
            List<TsValue> tsValues2 = a.get(1);
            calculateSum(tsValues2, map2);
        });
        map1.putAll(map2);

        return map1;


    }

    @Override
    public Map<Long, BigDecimal> areaStatistic1(List<EnergyStatisticTypeBo> energyStatisticTypeBos) {
        Map<Long, BigDecimal> map = new LinkedHashMap<>();

        List<List<List<TsValue>>> collect1 = energyStatisticTypeBos
                .stream()
                .map(this::deviceStatistic1
                ).collect(Collectors.toList());
        List<List<TsValue>> resultList = new ArrayList<>();
        collect1.forEach(resultList::addAll);
        resultList.forEach(tsValues -> calculateSum(tsValues,map));
        return map;
    }


    private void calculateSum(List<TsValue> tsValueList, Map<Long, BigDecimal> sumMap) {
        for (TsValue tsValue : tsValueList) {
            long ts = tsValue.getTs();
            BigDecimal bdValue = new BigDecimal(tsValue.getValue());
            bdValue = bdValue.setScale(2, RoundingMode.HALF_UP);
            if (sumMap.containsKey(ts)) {
                BigDecimal currentSum = sumMap.get(ts);
                BigDecimal newSum = currentSum.add(bdValue);
                newSum = newSum.setScale(2, RoundingMode.HALF_UP);
                sumMap.put(ts, newSum);
            } else {
                sumMap.put(ts, bdValue);
            }
        }
    }



    /**
     *  处理时间颗粒度
     * @param timeParticles 0-日 1-月 2-年
     * @return
     */
    private InfluxWindowBo handlerTimeParticles(int timeParticles) {
        InfluxWindowBo influxWindowBo = new InfluxWindowBo();
        influxWindowBo.setFunction(WindowFunction.LAST);
        influxWindowBo.setTime(1);
        switch (timeParticles) {
            case 0:
                influxWindowBo.setUnit(WindowTimeIntervalUnit.HOUR);
                break;
            case 1:
                influxWindowBo.setUnit(WindowTimeIntervalUnit.DAY);
                break;
            case 2:
                influxWindowBo.setUnit(WindowTimeIntervalUnit.Month);
                break;

        }
        return influxWindowBo;
    }

    private Date handlerCurrentEndTime(int timeParticles, Date startTime) {
        // 创建日历对象，并设置起始时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        switch (timeParticles) {
            case 0: // 日
                calendar.add(Calendar.DAY_OF_MONTH, 1); // 将日期加一天
                calendar.add(Calendar.HOUR_OF_DAY, 1);
                break;
            case 1: // 月
                calendar.add(Calendar.MONTH, 1); // 将月份加一
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                break;
            case 2: // 年
                calendar.add(Calendar.YEAR, 1); // 将年份加一
                calendar.add(Calendar.MONTH, 1);
                break;
            default:
                // 非法的时间颗粒度，可以根据需求进行异常处理
                break;
        }
        return calendar.getTime();
    }

    /**
     * 处理查询时间
     *
     * @param timeParticles 时间颗粒度 查询颗粒度 0-日 1-月 2-年
     * @param statisticType 统计类型 统计 0-同比 1-环比
     * @param startTime     起始时间
     * @return
     */
    private List<InfluxTimeBo> handlerStatisticsTime(int timeParticles, int statisticType, Date startTime) {
        InfluxTimeBo bo1 = new InfluxTimeBo();
        bo1.setStartTime(startTime);
        bo1.setEndTime(handlerCurrentEndTime(timeParticles, startTime));
        InfluxTimeBo bo2 = new InfluxTimeBo();
        bo2.setStartTime(getStatisticDate(statisticType, timeParticles, bo1.getStartTime()));
        bo2.setEndTime(getStatisticDate(statisticType, timeParticles, bo1.getEndTime()));
        List<InfluxTimeBo> result = new ArrayList<>();
        result.add(bo1);
        result.add(bo2);
        return result;

    }


    private Date getStatisticDate(int statisticType, int timeParticles, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 同比的结束时间为起始时间的前一年
        if (statisticType == 0) {

            calendar.add(Calendar.YEAR, -1);
        } else if (statisticType == 1) {
            //环比的结束时间为起始时间根据时间颗粒度进行调整
            switch (timeParticles) {
                case 0: // 日
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    break;
                case 1: // 月
                    calendar.add(Calendar.MONTH, -1);
                    break;
                case 2: // 年
                    calendar.add(Calendar.YEAR, -1);
                    break;
                default:
                    // 非法的时间颗粒度，可以根据需求进行异常处理
                    break;
            }
        }
        return calendar.getTime();
    }

    @Override
    public Map<Long, BigDecimal> areaStatisticQuota(Map<Long, BigDecimal> statisticDeviceData, int energyType) {
        LinkedHashMap<Long, BigDecimal> result = new LinkedHashMap<>();
        Random random = new Random();
        statisticDeviceData.forEach((key, value) -> {
            double randomNumber;
            if (energyType == 2) {
                randomNumber = 0.7 + 0.15 * random.nextDouble();
            } else {
                randomNumber = 1.08 + 0.07 * random.nextDouble();
            }
            BigDecimal bigDecimal = new BigDecimal(randomNumber);

            result.put(key, value.multiply(new BigDecimal(randomNumber)).setScale(2, RoundingMode.HALF_UP));

        });
        return result;
    }

    @Override
    public QuotaVo handlerResultQuotaVo(Map<Long, BigDecimal> statisticDeviceData, Map<Long, BigDecimal> quotaData, Integer timeParticles, Date startTime) {

        QuotaVo quotaVo  = new QuotaVo();


        List<Date> currentDates = new ArrayList<>();
        Date currentDate = startTime;
        switch (timeParticles) {
            //日
            case 0:
                currentDates.add(currentDate);
                for (int i = 0; i < 23; i++) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);
                    calendar.add(Calendar.HOUR_OF_DAY, 1);
                    currentDate = calendar.getTime();
                    currentDates.add(currentDate);
                }
                break;
            //月
            case 1:
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startTime);
                int currentMonth = calendar.get(Calendar.MONTH);
                int startMonth = calendar.get(Calendar.MONTH);
                while (startMonth == currentMonth) {
                    currentDate = calendar.getTime();
                    currentDates.add(currentDate);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    currentMonth = calendar.get(Calendar.MONTH);
                }
                break;
            //年
            case 2:
                currentDates.add(currentDate);
                for (int i = 0; i < 11; i++) {
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(currentDate);
                    calendar1.add(Calendar.MONTH, 1);
                    currentDate = calendar1.getTime();
                    currentDates.add(currentDate);
                }
                break;
        }


        SimpleDateFormat dateFormat = null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat format3 = new SimpleDateFormat("yyyy");
        switch (timeParticles) {
            case 0:
                quotaVo.setXUnit("时");
                dateFormat = new SimpleDateFormat("HH");
                break;
            case 1:
                quotaVo.setXUnit("日");
                dateFormat = new SimpleDateFormat("dd");
                break;
            case 2:
                quotaVo.setXUnit("月");
                dateFormat = new SimpleDateFormat("MM");
                break;
        }
        SimpleDateFormat finalDateFormat = dateFormat;
        quotaVo.setXAxis(currentDates.stream().map(finalDateFormat::format).collect(Collectors.toList()));

        Map<String, List<Double>> yDataMap = new HashMap<>();
        quotaVo.setYDataMap(yDataMap);
        //设置y1data
        yDataMap.put("energy", currentDates.stream().map(date -> {
            BigDecimal bigDecimal = statisticDeviceData.get(date.getTime());
            if (bigDecimal == null || bigDecimal.doubleValue() < 0) {
                return 0.0;
            }
            return bigDecimal.doubleValue();
        }).collect(Collectors.toList()));

        //设置y2data
        yDataMap.put("quota", currentDates.stream().map(date -> {
            BigDecimal bigDecimal = quotaData.get(date.getTime());
            if (bigDecimal == null || bigDecimal.doubleValue() < 0) {
                return 0.0;
            }
            return bigDecimal.doubleValue();
        }).collect(Collectors.toList()));

        return quotaVo;
    }

    @Override
    public List<PieChartVo> queryPieChartVosList(List<ProductStatisticIncreaseLastBo> productStatisticIncreaseLastBos) {
        return   productStatisticIncreaseLastBos
                .stream()
                .flatMap(bo -> {
                    Date endTime = handlerCurrentEndTime(bo.getTimeParticles(), bo.getStartTime());
                    InfluxWindowBo influxWindowBo = handlerTimeParticles(bo.getTimeParticles());
                    FluxQueryContainer<NormalMeasurement> firstContainer = influxDbService.queryAllOfWindow(bo.getAreaId().toString(), bo.getProductId().toString(), bo.getIdentity(), bo.getStartTime(), endTime, influxWindowBo.getUnit(), influxWindowBo.getFunction(), influxWindowBo.getTime(), FluxFunction.FIRST_FUNCTION, FillType.IGNORE_VALUE);
                    FluxQueryContainer<NormalMeasurement> endContainer = influxDbService.queryAllOfWindow(bo.getAreaId().toString(), bo.getProductId().toString(), bo.getIdentity(), bo.getStartTime(), endTime, influxWindowBo.getUnit(), influxWindowBo.getFunction(), influxWindowBo.getTime(), FluxFunction.LAST_FUNCTION, FillType.IGNORE_VALUE);
                    return firstContainer.getResultMap().keySet().stream()
                            .filter(normalMeasurement -> !(endContainer.getSimilarMeasudirectionEntries(normalMeasurement).isEmpty()))
                            .map(normalMeasurement -> {
                                PieChartVo pieChartVo = new PieChartVo();
                                TsKvEntry firstTsKv = firstContainer.getResultMap().get(normalMeasurement).stream().findFirst().get();
                                TsKvEntry lastTsKv = endContainer.getSimilarMeasudirectionEntries(normalMeasurement).values().stream().findFirst().get().stream().findFirst().get();
                                Double result = 0.0;
                                if (firstTsKv != null && lastTsKv != null) {
                                    Double firstDouble = firstTsKv.getDoubleValue().orElse(0.0);
                                    Double lastDouble = lastTsKv.getDoubleValue().orElse(0.0);
                                    result = lastDouble - firstDouble;
                                    BigDecimal bigDecimal = new BigDecimal(result);
                                    result = bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
                                }
                                if (result < 0.0) {
                                    result = 0.0;
                                }
                                pieChartVo.setValue(result);
                                pieChartVo.setName(deviceService.selectDeviceByDeviceId(normalMeasurement.getDeviceID()).getDeviceName());
                                return pieChartVo;
                            });
                })
                .collect(Collectors.toList());

    }

}
