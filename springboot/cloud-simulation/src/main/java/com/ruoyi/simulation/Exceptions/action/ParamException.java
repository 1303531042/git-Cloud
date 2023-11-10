package com.ruoyi.simulation.Exceptions.action;

import com.ruoyi.common.exception.base.BaseException;

/**
 * 参数异常类
 * 
 * @author ruoyi
 */
public class ParamException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public ParamException(String code, Object[] args)
    {
        super("param", code, args, null);
    }

}
