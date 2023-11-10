package com.ruoyi.gateway.tools.db;

import org.springframework.jdbc.core.SqlParameterValue;

import java.util.List;

public interface MapEntityMeta extends DBMeta {

    @Override
    List<SqlParameterValue> getParams(Object entity);
}
