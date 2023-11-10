package com.ruoyi.common.influxdb.enumeration;


/**
 * 窗口时间单位枚举类
 */
public enum WindowTimeIntervalUnit {
    /**
     * 秒
     */
    SECOND("s", 1000L),
    /**
     * 分钟
     */
    MINUTE("m", 60000L),
    /**
     * 小时
     */
    HOUR("h", 3600000L),
    /**
     * 天
     */
    DAY("d", 86400000L),
    /**
     * 月
     */
    Month("mo", 31556952000L),

    /**
     * 年
     */
    YEAR("y", 31556952000L);


    public Long getInterval() {
        return interval;
    }

    /**
     * 单位
     */
    private String unit;
    private Long interval;

    WindowTimeIntervalUnit(String unit, Long interval) {
        this.unit = unit;
        this.interval = interval;

    }

    public String getUnit() {
        return this.unit;
    }


    }
