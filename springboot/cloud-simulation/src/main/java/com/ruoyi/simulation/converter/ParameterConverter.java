package com.ruoyi.simulation.converter;

import org.springframework.core.convert.ConversionException;

/**
 * @auther: KUN
 * @date: 2023/9/26
 * @description: 参数类型转换器
 */
public interface ParameterConverter {
    Object convert(String value) throws ConversionException;
}
