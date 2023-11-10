package com.ruoyi.common.exception.influx;

/**
 * @auther: KUN
 * @date: 2023/7/13
 * @description: 该column 在Measudirections中不存在
 */
public class ColumnNotExistException extends InfluxException {

    public ColumnNotExistException(String columnName) {
        super("column："+ columnName+"在Measudirections不存在");
    }
}
