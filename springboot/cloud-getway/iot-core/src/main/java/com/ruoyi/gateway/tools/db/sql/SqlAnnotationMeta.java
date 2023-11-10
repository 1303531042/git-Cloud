package com.ruoyi.gateway.tools.db.sql;

import com.ruoyi.gateway.tools.db.FieldMeta;
import com.ruoyi.gateway.tools.db.IdType;
import com.ruoyi.gateway.tools.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于注解的sql元数据
 * @see IotTable
 */
public abstract class SqlAnnotationMeta implements SqlMeta {

    private String insertSql;
    private String paramsSql;
    private String tableName;
    private Class<?> entityClass;
    private List<FieldMeta> fieldMetas;

    public SqlAnnotationMeta(Class<?> entityClass) {
        this.entityClass = entityClass;
        this.fieldMetas = new ArrayList<>();
        this.resolveEntityTypeToMetas(entityClass);
        this.resolveInsertSql();
    }

    public SqlAnnotationMeta(String tableName, List<FieldMeta> fieldMetas) {
        this.tableName = tableName;
        this.fieldMetas = fieldMetas;
        this.resolveInsertSql();
    }

    protected void resolveEntityTypeToMetas(Class<?> entityClass) {
        IotTable table = entityClass.getAnnotation(IotTable.class);
        if(table == null) {
            throw new IllegalArgumentException("类["+entityClass.getSimpleName()+"]必须包含注解["+IotTable.class.getSimpleName()+"]");
        }

        this.tableName = table.value();

        Field[] fields = entityClass.getDeclaredFields();
        if(fields != null) {
            for (Field item : fields) {
                if(!item.isAccessible()) {
                    item.setAccessible(true);
                }

                IotTableId fieldId = item.getAnnotation(IotTableId.class);
                if(fieldId != null) {
                    fieldMetas.add(0, new IotTableIdMeta(fieldId, item));
                }

                IotField field = item.getAnnotation(IotField.class);
                if(field != null) {
                    fieldMetas.add(new IotFieldMeta(field, item));
                }
            }
        }
    }

    protected void resolveInsertSql() {
        StringBuilder sb = new StringBuilder("insert into ")
                .append(this.getTableName()).append('(');

        this.fieldMetas.forEach(meta -> {
            sb.append(meta.getName()).append(',');
        });

        // 删除最后一个逗号
        sb.deleteCharAt(sb.length() - 1).append(") values ");
        this.insertSql = sb.toString();

        // 清空
        sb.delete(0, sb.length()).append('(');

        this.fieldMetas.forEach(meta -> {
            sb.append('?').append(',');
        });

        this.paramsSql = sb.deleteCharAt(sb.length() - 1).append(')').toString();
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public IdType getType() {
        return null;
    }

    @Override
    public String getStatement() {
        return insertSql + paramsSql;
    }

    @Override
    public String getStatement(int size) {
        if(size == 1) {
            return this.getStatement();
        } else {
            StringBuilder sb = new StringBuilder(insertSql);
            for(int i=0; i<size; i++) {
                sb.append(paramsSql).append(',');
            }

            return sb.deleteCharAt(sb.length() - 1).toString();
        }
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public List<FieldMeta> getFieldMetas() {
        return fieldMetas;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }
}
