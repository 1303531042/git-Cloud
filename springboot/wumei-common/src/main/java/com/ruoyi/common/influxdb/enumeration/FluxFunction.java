package com.ruoyi.common.influxdb.enumeration;

public enum FluxFunction {

    NULL_FUNCTION(-1, "不使用函数"),

    /**
     * 累计函数模版
     */
    CUMULATIVE_SUM_FUNCTION(0, "累计函数"),

    /**
     * 非负差异的累计和模版
     */
    INCREASE_FUNCTION(1, "非负差异的累计和"),

    /**
     * 非空第一个值模版
     */
    FIRST_FUNCTION(2, "非空第一个值"),

    /**
     * 非空最后一个值模版
     */
    LAST_FUNCTION(3, "非空最后一个值"),

    /**
     * 平均数模版
     */
    MEAN_FUNCTION(4, "平均数"),
    /**
     * 减去上一个值
     */
    DIFFERENCE(5, "减去上一个值");


    private final int serial;
    private final String name;

    FluxFunction(int serial, String name) {
        this.serial = serial;
        this.name = name;
    }

    public int getSerial() {
        return serial;
    }

    public String getName() {
        return name;
    }

    public static FluxFunction getInstance(int serial) {
        for (FluxFunction value : values()) {
            if (value.getSerial() == serial) {
                return value;
            }
        }
        return NULL_FUNCTION;
    }
}
