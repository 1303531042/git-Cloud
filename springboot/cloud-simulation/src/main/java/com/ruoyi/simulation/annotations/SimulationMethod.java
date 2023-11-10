package com.ruoyi.simulation.annotations;

import com.ruoyi.simulation.enums.RequestEnum;

import java.lang.annotation.*;
/**
 * 可用说明该类是与模拟设备层对接接口方法
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SimulationMethod {
    Class<? extends RequestEnum> EnumClass();
    int code() ;
}
