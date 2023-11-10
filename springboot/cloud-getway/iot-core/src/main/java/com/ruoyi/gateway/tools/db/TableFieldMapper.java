package com.ruoyi.gateway.tools.db;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

public class TableFieldMapper {

    public static int javaTypeToFieldType(Class type) {
        if(Integer.class == type || int.class == type) {
            return Types.INTEGER;
        } else if(Double.class == type || double.class == type) {
            return Types.DOUBLE;
        } else if(Long.class == type || long.class == type) {
            return Types.BIGINT;
        } else if(Float.class == type || float.class == type) {
            return Types.FLOAT;
        } else if(Short.class == type || short.class == type) {
            return Types.SMALLINT;
        } else if(Byte.class == type || byte.class == type) {
            return Types.TINYINT;
        } else if(BigDecimal.class == type) {
            return Types.DECIMAL;
        } else if(String.class == type) {
            return Types.VARCHAR;
        } else if(Date.class.isAssignableFrom(type)) {
            if(Timestamp.class == type) {
                return Types.TIMESTAMP;
            } else if(Time.class == type) {
                return Types.TIME;
            } else {
                return Types.TIMESTAMP;
            }
        } else if(Boolean.class == type) {
            return Types.BOOLEAN;
        } else {
            return Types.NULL;
        }
    }

    public static int valueToTableType(Object value) {
        if(value != null) {
            if(value instanceof Number) {
                if(value instanceof Integer) {
                    return Types.INTEGER;
                } else if(value instanceof Long) {
                    return Types.BIGINT;
                } else if(value instanceof Double) {
                    return Types.DOUBLE;
                } else if(value instanceof Float) {
                    return Types.FLOAT;
                } else if(value instanceof Short) {
                    return Types.SMALLINT;
                } else if(value instanceof Byte) {
                    return Types.TINYINT;
                } else if(value instanceof BigDecimal) {
                    return Types.DECIMAL;
                } else {
                    return Types.OTHER;
                }
            } else if(value instanceof Boolean) {
                return Types.BOOLEAN;
            } else if(value instanceof Date) {
                return Types.TIMESTAMP;
            } else {
                return Types.VARCHAR;
            }
        } else {
            return Types.OTHER;
        }
    }
}
