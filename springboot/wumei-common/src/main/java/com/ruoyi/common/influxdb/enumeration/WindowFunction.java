package com.ruoyi.common.influxdb.enumeration;

/**
 * 窗口聚合方法
 */
public enum WindowFunction {
    /**
     * 求和
     */
    SUM("fn: sum"),
    /**
     * 第一个非空值
     */
    FIRST("fn: first"),
    /**
     * 非空值最后一个
     */
    LAST("fn: last"),
    /**
     * 累计数量
     */
    COUNT("fn:count"),
    /**
     * 最大值
     */
    MAX("fn:max"),
    /**
     * 最小值
     */
    MIN("fn:min"),
    /**
     * 中位数
     */
    MEDIAN("fn:median"),
    /**
     * 众数
     */
    MODE("fn:mode"),
    /**
     * 离散程度
     */
    SPREAD("fn:spread");



    /**
     *
     */
    private String function;

    WindowFunction(String function) {
        this.function = function;

    }

    public String getFunction() {
        return this.function;
    }

}
