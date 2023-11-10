package com.ruoyi.common.influxdb;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Bucket {
    String value();
}
