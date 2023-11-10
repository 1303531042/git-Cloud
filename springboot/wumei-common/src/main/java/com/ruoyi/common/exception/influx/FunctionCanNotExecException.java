package com.ruoyi.common.exception.influx;

/**
 * @auther: KUN
 * @date: 2023/7/13
 * @description: 函数不能执行
 */
public class FunctionCanNotExecException  extends InfluxException {
    public FunctionCanNotExecException() {
        super("存在column非Number类型：influx函数不能执行");
    }

}
