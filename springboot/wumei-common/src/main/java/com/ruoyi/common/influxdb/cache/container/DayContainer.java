package com.ruoyi.common.influxdb.cache.container;

import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.kv.entry.TsKvEntry;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/8/7
 * @description: 日容器
 */
public class DayContainer<T extends Measudirection> extends AbstractContainer<T> {
    /**
     * 30d
     */
    private final Long defaulTtllBeforeNow =2592000000L ;

    /**
     * 30s
     */
    private final Long defaulTtlAfterNow = 30000L;

    public DayContainer() {

    }

    public DayContainer(Long startTime, Long endTime) {
        super(startTime, endTime);
    }

    public DayContainer(Long startTime, Long endTime, List<TsKvEntry> datas) {
        super(startTime, endTime, datas);
    }

    /**
     * 获取默认 该容器的结束时间在now之前 缓存保存时间 单位毫秒
     */
    @Override
    protected Long defaultTtlBeforeNow() {
        return defaulTtllBeforeNow;
    }

    /**
     * 获取默认 该容器的结束时间在now之后 缓存保存时间 单位毫秒
     */
    @Override
    protected Long defaultTtlAfterNow() {
        return defaulTtlAfterNow;
    }

    /**
     * 点与点的距离 单位毫秒
     */
    @Override
    public Interval interval() {
        return Interval.DAY_INTERVAL;
    }


}
