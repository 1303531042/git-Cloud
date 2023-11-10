package com.ruoyi.gateway.tools.db.rdbms;

import com.ruoyi.gateway.tools.db.FieldMeta;
import com.ruoyi.gateway.tools.db.sql.SqlAnnotationMeta;
import com.ruoyi.gateway.tools.annotation.IotTable;

import java.util.List;

public class RdbmsMeta extends SqlAnnotationMeta {

    /**
     * 包含有注解IotTable的类
     * @see IotTable
     * @param entityClass
     */
    public RdbmsMeta(Class<?> entityClass) {
        super(entityClass);
    }

    /**
     * 自定义
     * @param tableName
     * @param fieldMetas
     */
    public RdbmsMeta(String tableName, List<FieldMeta> fieldMetas) {
        super(tableName, fieldMetas);
    }
}
