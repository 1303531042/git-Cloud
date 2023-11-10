package com.ruoyi.gateway.tools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Types;

/**
 * 表字段
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface IotField {

    /**
     * 表字段名(默认实体类字段名)
     * @return
     */
    String value() default "";

    /**
     * 表字段类型(默认实体类字段类型)
     * @see java.sql.Types
     * @return
     */
    int type() default Types.NULL;

    /**
     * 保留小数
     * @return
     */
    int scale() default -1;
}
