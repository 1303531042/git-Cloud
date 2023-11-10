package com.ruoyi.common.influxdb;

import com.ruoyi.common.influxdb.kv.DataType;
import java.lang.annotation.*;

/**
 * 说明 column数据类型
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ColumnType {

    DataType type();
}
