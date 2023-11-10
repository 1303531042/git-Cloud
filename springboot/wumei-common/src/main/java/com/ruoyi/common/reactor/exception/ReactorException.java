package com.ruoyi.common.reactor.exception;

import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/6/23
 * @description: 事件反应器异常
 */
@Data
public class ReactorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    public ReactorException(String module, String code, Object[] args, String defaultMessage)
    {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public ReactorException(String module, String code, Object[] args)
    {
        this(module, code, args, null);
    }

    public ReactorException(String module, String defaultMessage)
    {
        this(module, null, null, defaultMessage);
    }

    public ReactorException(String code, Object[] args)
    {
        this(null, code, args, null);
    }

    public ReactorException(String defaultMessage)
    {
        this(null, null, null, defaultMessage);
    }
}
