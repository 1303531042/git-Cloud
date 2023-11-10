package com.ruoyi.gateway.tools.db;

/**
 * 表字段元信息
 */
public interface FieldMeta {

    /**
     * 返回表字段名
     * @return
     */
    String getName();

    /**
     * 表字段类型
     * @return
     */
    int getType();
}
