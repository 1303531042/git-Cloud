package com.ruoyi.common.influxdb.cache.container;

import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.kv.entry.TsKvEntry;
import java.util.Date;
import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/8/7
 * @description: 容器抽象类
 */
public abstract class AbstractContainer<T extends Measudirection> implements Container<T> {

    /**
     * 该容器起始时间
     */
    private Long startTime;
    /**
     * 该容器终止时间
     */
    private Long endTime;

    private List<TsKvEntry> datas;
    /**
     * 该容器的结束时间在now之前 缓存保存时间 单位毫秒
     */
    private Long ttlBeforeNow;

    /**
     * 该容器的结束时间在now之后 缓存保存时间 单位毫秒
     */
    private Long ttlAfterNow;

    private T key;

    public AbstractContainer() {

    }

    public AbstractContainer(Long startTime, Long endTime) {
        this(startTime, endTime, null);
    }

    public AbstractContainer(Long startTime, Long endTime, List<TsKvEntry> datas) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.datas = datas;
    }


    /**
     * 该容器起始时间
     */
    @Override
    public Long getStartTime() {
        return startTime;
    }

    /**
     * 该容器结束时间
     */
    @Override
    public Long getEndTime() {
        return endTime;
    }

    /**
     * 获取该容器数据列表
     */
    @Override
    public List<TsKvEntry> getDatas() {
        return datas;
    }

    /**
     * 设置该容器数据列表
     */
    @Override
    public void setDatas(List<TsKvEntry> datas) {
        this.datas = datas;
    }

    /**
     * 当前容器数量
     */
    @Override
    public Integer size() {
        if (datas != null) {
            return datas.size();
        }
        return 0;
    }

    /**
     * 该容器的结束时间在now之前 缓存保存时间 单位毫秒
     */
    public Long ttlBeforeNow() {
        if (ttlBeforeNow == null) {
            return defaultTtlBeforeNow();
        }
        return ttlBeforeNow;
    }

    /**
     * 该容器的结束时间在now之后 缓存保存时间 单位毫秒
     */
    public Long ttlAfterNow() {
        if (ttlAfterNow == null) {
            return defaultTtlAfterNow();
        }
        return ttlAfterNow;
    }

    @Override
    public T getKey() {
        return key;
    }


    @Override
    public void setKey(T key) {
        this.key = key;
    }

    public void setTtlBeforeNow(Long ttlBeforeNow) {
        this.ttlBeforeNow = ttlBeforeNow;
    }

    @Override
    public void setTtlAfterNow(Long ttlAfterNow) {
        this.ttlAfterNow = ttlAfterNow;
    }

    @Override
    public Long getTtl(Date endTime) {
        if (endTime.before(new Date())) {
            return ttlBeforeNow();
        }
        return ttlAfterNow();
    }

    /**
     * 获取默认 该容器的结束时间在now之前 缓存保存时间 单位毫秒
     */
    protected abstract Long defaultTtlBeforeNow();

    /**
     * 获取默认 该容器的结束时间在now之后 缓存保存时间 单位毫秒
     */
    protected abstract Long defaultTtlAfterNow();
}
