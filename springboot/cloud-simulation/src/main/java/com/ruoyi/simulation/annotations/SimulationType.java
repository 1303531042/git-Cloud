package com.ruoyi.simulation.annotations;


import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SimulationType {
    /**
     * 返回该simulation interface type
     * @return
     */
    String value();

}
