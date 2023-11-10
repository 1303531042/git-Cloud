package com.ruoyi.common.influxdb.kv;

/**
 * 时序数据查询包装
 */
public interface TsKvQuery {
    /**
     * 此次查询id
     */
    int getId();

    /**
     * 此次查询key的名字
     */
    String getKey();

    /**
     * 此次查询的起始时间 单位毫秒
     */
    long getStartTs();

    /**
     * 此次查询的结束时间 单位毫秒
     */
    long getEndTs();

}
