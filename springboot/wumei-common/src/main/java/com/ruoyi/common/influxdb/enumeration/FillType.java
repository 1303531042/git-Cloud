package com.ruoyi.common.influxdb.enumeration;

/**
 * 补全数据方式枚举
 */
public enum FillType {

    /**
     * 使用前一个值
     */
    USE_PREVIOUS(null),

    /**
     * 使用默认值补全
     */
    DEFAULT_VALUE(null),

    /**
     * 使用null补全
     */
    NULL_VALUE(null),
    /**
     * 忽略间隔，不补全
     */
    IGNORE_VALUE(null);

    Object value;

     FillType(Object value) {
         this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
