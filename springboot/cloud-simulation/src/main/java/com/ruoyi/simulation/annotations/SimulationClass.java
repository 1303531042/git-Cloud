package com.ruoyi.simulation.annotations;

import com.ruoyi.simulation.compoment.SimulationInterface;

import java.lang.annotation.*;

/**
 * 可用说明该类是与模拟设备层对接接口
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SimulationClass {
    /**
     * 返回的是对应的模拟层设备实现类
     */
    Class<? extends SimulationInterface> value();
}
