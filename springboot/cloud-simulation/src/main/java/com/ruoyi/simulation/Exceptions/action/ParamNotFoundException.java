package com.ruoyi.simulation.Exceptions.action;

/**
 * 参数值不存在异常类
 * 
 * @author ruoyi
 */
public class ParamNotFoundException extends ParamException
{
    private static final long serialVersionUID = 1L;

    public ParamNotFoundException(Integer id)
    {
        super("执行requestEnum对应方法所需要参数不存在", new Object[]{id});

    }
}
