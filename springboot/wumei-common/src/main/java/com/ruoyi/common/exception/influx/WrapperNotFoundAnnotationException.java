package com.ruoyi.common.exception.influx;

/**
 * @auther: KUN
 * @date: 2023/7/10
 * @description: Measurement对象上缺少必要的注解
 */
public class WrapperNotFoundAnnotationException extends InfluxException {
    private static final long serialVersionUID = 1L;

    public WrapperNotFoundAnnotationException(String annotationName) {
        super(annotationName + "： 该注解中不存在");
    }

}
