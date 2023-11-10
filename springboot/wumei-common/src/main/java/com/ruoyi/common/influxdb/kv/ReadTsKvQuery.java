package com.ruoyi.common.influxdb.kv;

public interface ReadTsKvQuery extends TsKvQuery {

    long getInterval();

    int getLimit();

    Aggregation getAggregation();

    String getOrder();

}
