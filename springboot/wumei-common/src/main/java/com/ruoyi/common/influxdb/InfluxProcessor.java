package com.ruoyi.common.influxdb;

import com.ruoyi.common.influxdb.cache.CacheProcessor;
import com.ruoyi.common.influxdb.cache.ContainerCacheHandler;
import com.ruoyi.common.influxdb.cache.container.Container;
import com.ruoyi.common.influxdb.cache.container.Interval;
import com.ruoyi.common.influxdb.enumeration.FillType;
import com.ruoyi.common.influxdb.enumeration.FluxFunction;
import com.ruoyi.common.influxdb.enumeration.WindowFunction;
import com.ruoyi.common.influxdb.enumeration.WindowTimeIntervalUnit;
import com.ruoyi.common.influxdb.handler.*;
import com.ruoyi.common.influxdb.kv.entry.TsKvEntry;
import com.ruoyi.common.influxdb.wrapper.IntervalWrapper;
import com.ruoyi.common.influxdb.wrapper.query.FluxQueryWrapper;
import com.ruoyi.common.influxdb.wrapper.query.SimpleQueryWrapper;
import com.ruoyi.common.redis.utils.RedisUtils;
import com.ruoyi.common.utils.reflect.ReflectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public abstract class InfluxProcessor<T extends Measudirection> implements CacheProcessor<T>{
    protected FluxFunctionHandlerFactory<T> fluxFunctionHandlerFactory;
    protected WindowFunctionHandlerFactory<T> windowFunctionHandlerFactory;
    protected InfluxBaseMapper<T> influxBaseMapper;
    protected ContainerCacheHandler<T> containerCacheHandler = new ContainerCacheHandler<>();
    protected Map<FluxQueryWrapper<T>, List<String>> simpleCacheKeysMap = new ConcurrentHashMap<>();

    protected FluxQueryContainerFactory<T> fluxQueryContainerFactory = new FluxQueryContainerFactory<>();


    public InfluxProcessor(InfluxBaseMapper<T> influxBaseMapper) {
        fluxFunctionHandlerFactory = new FluxFunctionHandlerFactory<>();
        windowFunctionHandlerFactory = new WindowFunctionHandlerFactory<>();
        this.influxBaseMapper = influxBaseMapper;
    }

    /**
     * 查询原始数据不使用窗口
     * @param fillType
     * @param fluxFunction
     * @return
     */
    public FluxQueryContainer queryAllOfPoint(FluxQueryWrapper<T> wrapper,FillType fillType, FluxFunction fluxFunction) {

        //处理使用influx函数
        handlerFunction(wrapper, fluxFunction);
        //处理窗口函数
        IntervalWrapper intervalWrapper = new IntervalWrapper(Interval.POINTER_INTERVAL, Interval.POINTER_INTERVAL.getInterval());
        //查询
        Map<T, List<TsKvEntry>>  deviceTsKvEntryMap = queryAllInterval(intervalWrapper.getInterval(), wrapper);
        //处理补全函数
        return handlerFill(wrapper, fillType, intervalWrapper, deviceTsKvEntryMap, wrapper.getStartTime(), wrapper.getEndTime());
    }


    /**
     * 使用窗口函数进行查询
     *
     * @param windowTimeIntervalUnit
     * @param windowFunction
     * @param time
     * @param startTime
     * @param endTime
     * @param fillType
     * @param fluxFunction
     * @return
     */
    protected FluxQueryContainer<T> doQueryAllOfWindow(FluxQueryWrapper<T> wrapper, Date startTime, Date endTime, WindowTimeIntervalUnit windowTimeIntervalUnit, WindowFunction windowFunction, Integer time, FluxFunction fluxFunction, FillType fillType) {

        //处理使用influx函数
        handlerFunction(wrapper, fluxFunction);
        //处理窗口函数
        IntervalWrapper intervalWrapper = handlerWindow(wrapper, windowTimeIntervalUnit, windowFunction, time);
        //查询
        Map<T, List<TsKvEntry>> deviceTsKvEntryMap = queryAllInterval(intervalWrapper.getInterval(), wrapper);
        //处理补全函数
        return handlerFill(wrapper, fillType, intervalWrapper, deviceTsKvEntryMap, startTime, endTime);
    }


    /**
     * 根据指定的时间间隔、设备ID、设备字段、起始时间和结束时间，查询并返回对应的TsKvEntry对象列表。
     * 方法根据不同的条件判断，可能会从缓存中获取数据，也可能直接查询数据库。
     */
    private Map<T, List<TsKvEntry>> queryAllInterval(Interval interval, FluxQueryWrapper<T> queryWrapper) {
        Map<T, List<TsKvEntry>> result;
        //如果该wrapper没有使用influx自带函数且他不是自由窗口则可以尝试从缓存中获取
        if ((!queryWrapper.useFunction()) && !interval.equals(Interval.FREE_INTERVAL)) {
            result = new HashMap<>();
            List<Container<T>> allContainers = new ArrayList<>();
            //将起始时间和结束时间规整为符合页容器的时间窗口集合
            List<TimeWrapper> timeWrappers = containerCacheHandler.getTimeWrappers(interval, queryWrapper.getStartTime(), queryWrapper.getEndTime());
            result = timeWrappers.stream().flatMap(timeWrapper -> {
                Date containerStartTime = timeWrapper.getStartTime();
                Date containerEndTime = timeWrapper.getEndTime();
                List<String> containerCacheKeys = getWrapperSimpleCacheKeys(queryWrapper)
                        .stream()
                        .map(simpleKey -> containerCacheHandler.containerCacheKey(interval, simpleKey, containerStartTime, containerEndTime))
                        .collect(Collectors.toList());
                //判断是否存在所有key需要的时间段如果都存在从缓存中获取否则进行查询
                boolean existAllKey = containerCacheKeys.stream().allMatch(RedisUtils::hasKey);
                List<Container<T>> containerList = new ArrayList<>();

                if (existAllKey) {
                    containerList = containerCacheKeys.stream().map(RedisUtils::<Container<T>>getCacheObject).collect(Collectors.toList());
                } else {
                    queryWrapper.rangeTime(containerStartTime, containerEndTime);
                    List<Container<T>> finalContainerList = containerList;
                    influxBaseMapper.selectList(queryWrapper)
                            .forEach((key, tsKvEntries) -> {
                                Container<T> container = Interval.getContainerInstance(interval, containerStartTime, containerEndTime);
                                container.setDatas(tsKvEntries);
                                container.setKey(key);
                                finalContainerList.add(container);
                                RedisUtils.setCacheObject(getMeasudirectionSimpleKey(key), container, Duration.ofMillis(container.getTtl(containerEndTime)));
                            });
                }

                return containerList.stream();

            }).collect(Collectors.toMap(Container::getKey, Container::getDatas));
        } else {
            result = influxBaseMapper.selectList(queryWrapper);
        }
        //清理此次wrapper缓存
        removeWrapperSimpleCacheKeys(queryWrapper);
        return result;
//            for (TimeWrapper timeWrapper : timeWrappers) {
//                Date containerStartTime = timeWrapper.getStartTime();
//                Date containerEndTime = timeWrapper.getEndTime();
//                //根据规则获取缓存key
//                List<String> containerCacheKeys = getWrapperSimpleCacheKeys(queryWrapper)
//                        .stream()
//                        .map(simpleKey -> containerCacheHandler.containerCacheKey(interval, simpleKey, containerStartTime, containerEndTime))
//                        .collect(Collectors.toList());
//                //判断redis是否存在容器缓存
//                boolean existAllKey = true;
//                Iterator<String> iterator = containerCacheKeys.iterator();
//                while (iterator.hasNext()) {
//                    String containerCacheKey = iterator.next();
//                    if (!RedisUtils.hasKey(containerCacheKey)) {
//                        existAllKey = false;
//                        break; // 跳出循环
//                    }
//                }
//
//                List<Container> containerList = new ArrayList<>();
//                //全部存在从缓存中获取
//                if (existAllKey) {
//                    containerList = containerCacheKeys.stream().map(RedisUtils::<Container>getCacheObject).collect(Collectors.toList());
//                } else {
//                    //从数据库读取并放入缓存
//                    queryWrapper.rangeTime(containerStartTime, containerEndTime);
//                    List<Container> finalContainerList = containerList;
//                    influxBaseMapper.selectList(queryWrapper)
//                            .forEach((key, tsKvEntries) -> {
//                                Container container = Interval.getContainerInstance(interval, containerStartTime, containerEndTime);
//                                container.setDatas(tsKvEntries);
//                                container.setKey(key);
//                                finalContainerList.add(container);
//                                RedisUtils.setCacheObject(getMeasudirectionSimpleKey(key), container, Duration.ofMillis(container.getTtl(containerEndTime)));
//                            });
//                }
//                allContainers.addAll(containerList);
//            }
//            //从容器中拿到符合查询时间范围内的点
//            result = containerCacheHandler.getConformToTimeTsKvEntrys(allContainers, queryWrapper.getStartTime(), queryWrapper.getEndTime());
//
//        } else {
//            //直接从influx中查询拿到结果
//            result = influxBaseMapper.selectList(queryWrapper);
//        }
//        return result;
    }

    /**
     * 处理使用influx函数
     * @param wrapper
     * @param fluxFunction
     */
    private void handlerFunction(FluxQueryWrapper<T> wrapper, FluxFunction fluxFunction) {
        FluxFunctionHandler.FunctionHandler<T> functionHandler = fluxFunctionHandlerFactory.createHandler(fluxFunction);
        functionHandler.handle(wrapper);
    }

    /**
     * 处理窗口函数
     *
     * @param windowTimeIntervalUnit
     * @param windowFunction
     * @param time
     * @param wrapper
     */
    private IntervalWrapper handlerWindow(FluxQueryWrapper<T> wrapper, WindowTimeIntervalUnit windowTimeIntervalUnit, WindowFunction windowFunction, Integer time) {
        FluxWindowHandler.WindowFunctionHandler<T> windowFunctionHandler = windowFunctionHandlerFactory.createHandler(windowTimeIntervalUnit, time);
        return windowFunctionHandler.handle(windowTimeIntervalUnit, time, windowFunction, wrapper);

    }

    /**
     * 处理补全函数
     *
     * @param wrapper
     * @param fillType
     * @param intervalWrapper
     * @param startTime
     * @param endTime
     * @return
     */
    private FluxQueryContainer<T>  handlerFill(FluxQueryWrapper<T> wrapper, FillType fillType, IntervalWrapper intervalWrapper, Map<T, List<TsKvEntry>> deviceTsKvEntryMap, Date startTime, Date endTime) {

        if (!wrapper.useArgFunction()) {
            deviceTsKvEntryMap.forEach((measudirection,tsKvEntries)->{
                tsKvEntries = FillDataHandler.fillResult(fillType, intervalWrapper, tsKvEntries, startTime, endTime);
                deviceTsKvEntryMap.put(measudirection, tsKvEntries);
            });
        }
        FluxQueryContainer<T> container = fluxQueryContainerFactory.createContainer();
        container.setResultMap(deviceTsKvEntryMap);
        return container;
    }


    /**
     * 插入单行数据
     *
     */
    public void savaMeasudirection(T measudirection) {
        influxBaseMapper.insert(measudirection);
    }

    /**
     * 插入多行数据
     */
    public void savaMeasudirectionList(List<T> measudirectionList) {
        influxBaseMapper.insertBatch(measudirectionList);
    }

    @Override
    public List<String> getWrapperSimpleCacheKeys(FluxQueryWrapper<T> wrapper) {
        List<String> list = simpleCacheKeysMap.get(wrapper);
        return list;
    }

    private void removeWrapperSimpleCacheKeys(FluxQueryWrapper<T> wrapper){
        simpleCacheKeysMap.remove(wrapper);
    }

    protected abstract void setWrapperSimpleCacheKeys(FluxQueryWrapper<T> wrapper,T measudirection);
}