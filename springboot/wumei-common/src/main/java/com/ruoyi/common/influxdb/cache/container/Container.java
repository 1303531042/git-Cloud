package com.ruoyi.common.influxdb.cache.container;

import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.kv.entry.TsKvEntry;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @auther: KUN
 * @date: 2023/8/7
 * @description: 类似页缓存机制 每次查询数据最小为一页
 */
public interface Container<T extends Measudirection> extends Serializable {


    /**
     *  该容器起始时间
     */
    Long getStartTime();

    /**
     * 该容器结束时间
     */
    Long getEndTime();

    /**
     * 获取这些数据的所属key对象
     * @return
     */
    T getKey();

    /**
     * 设置这些数据的所属key对象
     *
     * @return
     */
    void setKey(T key);


    /**
     * 获取该容器数据列表
     */
    List<TsKvEntry> getDatas();

    /**
     * 设置该容器数据列表
     */
    void setDatas(List<TsKvEntry> datas);


    /**
     * 当前容器数量
     */
    Integer size();

    /**
     * 点与点的距离 单位毫秒
     */
    Interval interval();

    void setTtlBeforeNow(Long ttlBeforeNow);

    void setTtlAfterNow(Long ttlAfterNow);

    /**
     *
     * @param endTime 该容器的结束时间
     * @return 获取该容器的ttl
     */
    Long getTtl(Date endTime);





}
