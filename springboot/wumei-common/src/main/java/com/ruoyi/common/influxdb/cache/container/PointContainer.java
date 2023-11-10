package com.ruoyi.common.influxdb.cache.container;

import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.kv.entry.TsKvEntry;
import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/8/7
 * @description: 点容器
 */
public class PointContainer<T extends Measudirection> extends AbstractContainer<T> {


    /**
     * 3d
     */
    private final Long defaultTtlBeforeNow = 259200000L;

    /**
     * 30s
     */
    private final Long defaultTtlAfterNow = 30000L;

    public PointContainer() {

    }


    public PointContainer(Long startTime, Long endTime) {
        super(startTime, endTime);
    }

    public PointContainer(Long startTime, Long endTime, List<TsKvEntry> datas) {
        super(startTime, endTime, datas);
    }


    /**
     * 获取默认 该容器的结束时间在now之前 缓存保存时间 单位毫秒
     */
    @Override
    protected Long defaultTtlBeforeNow() {
        return defaultTtlBeforeNow;
    }

    /**
     * 获取默认 该容器的结束时间在now之后 缓存保存时间 单位毫秒
     */
    @Override
    protected Long defaultTtlAfterNow() {
        return defaultTtlAfterNow;
    }

    /**
     * 点与点的距离 单位毫秒
     */
    @Override
    public Interval interval() {
        return Interval.POINTER_INTERVAL;
    }


}
