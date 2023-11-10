package com.ruoyi.gateway.tools.db;

import java.util.List;

/**
 * 数据存储元信息
 */
public interface DBMeta {

    /**
     * 返回表信息
     * @return
     */
    String getTableName();

    /**
     * 字段元列表
     * @return
     */
    List<FieldMeta> getFieldMetas();

    /**
     * 返回语句的参数列表
     * @param entity
     * @return
     */
    List<?> getParams(Object entity);

    String getStatement();

    String getStatement(int size);
}
