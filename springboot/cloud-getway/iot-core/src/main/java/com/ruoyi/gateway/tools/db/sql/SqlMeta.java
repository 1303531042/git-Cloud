package com.ruoyi.gateway.tools.db.sql;

import com.ruoyi.gateway.tools.annotation.IotField;
import com.ruoyi.gateway.tools.annotation.IotTableIdMeta;
import com.ruoyi.gateway.tools.annotation.IotFieldMeta;
import com.ruoyi.gateway.tools.db.DBMeta;
import com.ruoyi.gateway.tools.db.FieldMeta;
import com.ruoyi.gateway.tools.db.IdType;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 居于sql的数据存储元信息
 */
public interface SqlMeta extends DBMeta {

//    IdWorker worker = new IdWorker(3, 3);

    /**
     * 表字段标识
     * @return
     */
    String getId();

    /**
     * id字段类型
     * @return
     */
    IdType getType();

    @Override
    default List<SqlParameterValue> getParams(Object entity) {
        if(entity instanceof Map) {
            return resolveMapEntityParams((Map) entity);
        } else {
            List<SqlParameterValue> parameterValue = new ArrayList<>();

            for (int i=0; i< this.getFieldMetas().size(); i++) {
                FieldMeta meta = this.getFieldMetas().get(i);
                if(meta instanceof IotFieldMeta) {
                    IotFieldMeta fieldMeta = (IotFieldMeta) meta;
                    IotField field = fieldMeta.getIotField();
                    Object value = ReflectionUtils.getField((fieldMeta).getField(), entity);
                    if(field.scale() != -1) {
                        parameterValue.add(new SqlParameterValue(meta.getType(), field.scale(), value));
                    } else {
                        parameterValue.add(new SqlParameterValue(meta.getType(), value));
                    }
                } else if(meta instanceof IotTableIdMeta) {
                    Object value = ReflectionUtils.getField(((IotTableIdMeta) meta).getField(), entity);
                    parameterValue.add(new SqlParameterValue(meta.getType(), value));
                }
            }

            return parameterValue;
        }
    }

    default List<SqlParameterValue> resolveMapEntityParams(Map mapEntity) {
        List<? extends FieldMeta> fieldMetas = getFieldMetas();
        List<SqlParameterValue> parameterValues = new ArrayList<>();

        fieldMetas.forEach(item -> {
            Object value = mapEntity.get(item.getName());
            parameterValues.add(new SqlParameterValue(item.getType(), value));
        });

        return parameterValues;
    }
}
