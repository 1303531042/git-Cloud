package com.ruoyi.gateway.tools.annotation;

import com.ruoyi.gateway.tools.db.DefaultFieldMeta;
import com.ruoyi.gateway.tools.db.TableFieldMapper;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.Types;

public class IotTagMeta extends DefaultFieldMeta {

    private IotTag tag;

    private Field field;

    public IotTagMeta(IotTag tag, Field field) {
        super(tag.type(), tag.value());
        this.tag = tag;
        this.field = field;
        if(!StringUtils.hasText(this.getName())) {
            this.setName(field.getName());
        }
        if(this.getType() == Types.NULL) {
            this.setType(TableFieldMapper.javaTypeToFieldType(field.getType()));
        }
    }

    public IotTag getTag() {
        return tag;
    }

    public void setTag(IotTag tag) {
        this.tag = tag;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
