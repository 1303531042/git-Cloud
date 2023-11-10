package com.ruoyi.gateway.tools.annotation;

import com.ruoyi.gateway.tools.db.DBMeta;
import com.ruoyi.gateway.tools.db.FieldMeta;

import java.util.List;

public interface TagMeta extends DBMeta {

    /**
     * tag字段元列表
     * @see IotTag
     * @return
     */
    List<? extends FieldMeta> getTagMetas();
}
