package com.ruoyi.gateway.tools.annotation;

import com.ruoyi.gateway.tools.db.DefaultFieldMeta;
import com.ruoyi.gateway.tools.db.FieldMeta;
import com.ruoyi.gateway.tools.db.TableFieldMapper;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.Types;

/**
 * @see IotField
 * @see IotTable
 */
public class IotFieldMeta extends DefaultFieldMeta implements FieldMeta {

    private Field field;

    private IotField iotField;

    public IotFieldMeta(IotField iotField, Field field) {
        super(iotField.type(), iotField.value());
        this.field = field;
        this.iotField = iotField;
        // 通过java属性类型获取表字段类型
        if(iotField.type() == Types.NULL) {
            this.setType(TableFieldMapper.javaTypeToFieldType(field.getType()));
        }

        // 处理字段名
        if(!StringUtils.hasText(this.getName())) {
            this.setName(field.getName());
        }
    }

    public Field getField() {
        return field;
    }

    public IotField getIotField() {
        return iotField;
    }

}
