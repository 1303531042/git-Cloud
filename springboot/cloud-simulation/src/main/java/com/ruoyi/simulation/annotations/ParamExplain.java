package com.ruoyi.simulation.annotations;


import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ParamExplain {
    /**
     *
     * @return 解释参数 提供前端展示
     */
    String explain();

    /**
     * 前端展示参数id
     * @return
     */
    int id();

    /**
     *
     * @return 如果为true该参数在扫描时不需要注入该参数，在设备创建时才需要注入该参数
     */
    boolean ignoreBeforeInstance() default false;



}
