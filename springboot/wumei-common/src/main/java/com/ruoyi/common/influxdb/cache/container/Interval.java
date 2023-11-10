package com.ruoyi.common.influxdb.cache.container;

import java.util.Date;

/**
 * 点间隔
 */
public enum Interval {
    /**
     * 不限制点点间隔 使用自由窗口函数
     */
    FREE_INTERVAL("free", -1L,-1L),
    /**
     * 不限制点点间隔 使用原始点时间
     */
    POINTER_INTERVAL("point", 60000L,300000L),

    /**
     * 每个点的时间都是整小时时间点
     */
    HOUR_INTERVAL("hour",3600000L,86400000L),
    /**
     * 每个点的时间都是整天时间点
     */
    DAY_INTERVAL("day",86400000L,2592000000L);

    private String explain;
    private Long interval;
    /**
     * 容器页时间段
     */
    private Long containerInterval;

    Interval(String explain, Long interval, Long containerInterval) {
        this.explain = explain;
        this.interval = interval;
        this.containerInterval = containerInterval;
    }

    public String getExplain() {
        return explain;
    }

    public Long getInterval() {
        return interval;
    }

    public Long getContainerInterval() {
        return containerInterval;
    }

    public static Container getContainerInstance(Interval interval, Date startTime, Date endTime) {
        switch (interval) {
            case HOUR_INTERVAL:
                return new HourContainer<>(startTime.getTime(), endTime.getTime());
            case DAY_INTERVAL:
                return new DayContainer<>(startTime.getTime(), endTime.getTime());
            case POINTER_INTERVAL:
                return new PointContainer<>(startTime.getTime(), endTime.getTime());
            default:
                throw new RuntimeException("interval不存在");
        }

    }

}
