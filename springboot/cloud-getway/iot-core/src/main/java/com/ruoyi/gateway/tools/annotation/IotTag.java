package com.ruoyi.gateway.tools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Types;

/**
 * 表标签配置
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface IotTag {

    /**
     * 表字段标签名, 默认实体类字段名
     * @return
     */
    String value() default "";

    /**
     * 表字段类型, 默认实体类字段类型
     * @see java.sql.Types
     * @return
     */
    int type() default Types.NULL;
}
