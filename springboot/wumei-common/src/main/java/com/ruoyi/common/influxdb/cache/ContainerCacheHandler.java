package com.ruoyi.common.influxdb.cache;

import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.TimeWrapper;
import com.ruoyi.common.influxdb.cache.container.Container;
import com.ruoyi.common.influxdb.cache.container.Interval;
import com.ruoyi.common.influxdb.kv.entry.TsKvEntry;
import java.util.*;

/**
 * @auther: KUN
 * @date: 2023/8/8
 * @description: 容器缓存处理类
 */
public class ContainerCacheHandler<T extends Measudirection> {

    /**
     * 获取容器id
     *
     * @param interval  间隔
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @return 缓存key
     */
    public String containerCacheKey(Interval interval, String simpleKey, Date startTime, Date endTime) {
        return interval.getExplain() + "_" + simpleKey + startTime + ":" + endTime;
    }


    /**
     * 根据指定的时间间隔 (Interval)，将给定的起始时间 (startTime) 和结束时间 (endTime) 划分成一系列的时间段
     *
     * @param interval  指定的时间间隔
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @return
     */
    public List<TimeWrapper> getTimeWrappers(Interval interval, Date startTime, Date endTime) {

        List<TimeWrapper> timeWrappers = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        long intervalMillis = interval.getInterval(); // 规整时间参数
        long containerInterval = interval.getContainerInterval(); // 容器时间间隔
        long startTimeMillis = calendar.getTimeInMillis(); // 获取起始时间的毫秒数
        long roundedStartTimeMillis = (startTimeMillis / intervalMillis) * intervalMillis; // 对起始时间进行舍入
        calendar.setTimeInMillis(roundedStartTimeMillis);

        Date currentStartTime = calendar.getTime(); // 当前时间段的起始时间
        Date currentEndTime = new Date(currentStartTime.getTime() + containerInterval); // 当前时间段的结束时间

        while (currentEndTime.compareTo(endTime) <= 0) {

            TimeWrapper timeWrapper = new TimeWrapper(currentStartTime, currentEndTime); // 创建时间段对象

            timeWrappers.add(timeWrapper); // 将时间段对象添加到列表中
            currentStartTime = currentEndTime; // 更新当前时间段的起始时间为前一个时间段的结束时间
            currentEndTime = new Date(currentStartTime.getTime() + containerInterval); // 计算新的当前时间段的结束时间
        }

        // 如果结束时间未被时间间隔覆盖且startTiME，则添加最后一个时间段
        if (currentStartTime.compareTo(endTime) <= 0 && !Objects.equals(currentStartTime, currentEndTime)) {
            TimeWrapper timeWrapper = new TimeWrapper(currentStartTime, currentEndTime);
            timeWrappers.add(timeWrapper);
        }
        List<TimeWrapper> result = new ArrayList<>();
        timeWrappers
                .stream()
                .filter(timeWrapper -> !Objects.equals(timeWrapper.getStartTime(), timeWrapper.getEndTime()))
                .forEach(result::add);
        return result; // 返回生成的时间段列表
    }

    /**
     * 从容器集合中获取符合查询时间范围的点
     *
     * @param containers
     * @param startTime
     * @param endTime
     * @return
     */
    public Map<T, List<TsKvEntry>> getConformToTimeTsKvEntrys(List<Container<T>> containers, Date startTime, Date endTime) {

        Map<T, List<TsKvEntry>> result = new HashMap<>();
        containers.forEach(container -> {
            Date start = new Date(container.getStartTime());
            Date end = new Date(container.getEndTime());
            List<TsKvEntry> tsKvEntries = new ArrayList<>();
            if (startTime.before(start) && endTime.after(end)) {
                tsKvEntries.addAll(container.getDatas());
            } else {
                container.getDatas().forEach(tsKvEntry -> {
                    Date tsDate = new Date(tsKvEntry.getTs());
                    if ((Objects.equals(startTime, tsDate) || startTime.before(tsDate)) && endTime.after(tsDate)) {
                        tsKvEntries.add(tsKvEntry);
                    }
                });
            }
            result.put((T) container.getKey(), tsKvEntries);

        });
        return result;

    }






}
