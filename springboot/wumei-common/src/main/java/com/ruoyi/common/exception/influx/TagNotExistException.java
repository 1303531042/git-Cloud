package com.ruoyi.common.exception.influx;

/**
 * @auther: KUN
 * @date: 2023/7/13
 * @description: 该tag 在Measudirections中不存在
 */
public class TagNotExistException extends InfluxException {

    public TagNotExistException(String tagName) {
        super("tag："+ tagName+"在Measudirections不存在");
    }
}
