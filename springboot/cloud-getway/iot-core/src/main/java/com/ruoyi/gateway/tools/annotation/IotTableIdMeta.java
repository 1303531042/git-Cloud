package com.ruoyi.gateway.tools.annotation;

import com.ruoyi.gateway.tools.db.DefaultFieldMeta;
import com.ruoyi.gateway.tools.db.TableFieldMapper;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.Types;

public class IotTableIdMeta extends DefaultFieldMeta {

    private Field field;

    private IotTableId tableId;

    public IotTableIdMeta(IotTableId tableId, Field field) {
        super(tableId.type(), tableId.value());
        this.field = field;
        this.tableId = tableId;

        if(this.getType() == Types.NULL) {
            this.setType(TableFieldMapper.javaTypeToFieldType(field.getType()));
        }

        if(!StringUtils.hasText(this.getName())) {
            this.setName(field.getName());
        }
    }

    public Field getField() {
        return field;
    }

    public IotTableId getTableId() {
        return tableId;
    }
}
