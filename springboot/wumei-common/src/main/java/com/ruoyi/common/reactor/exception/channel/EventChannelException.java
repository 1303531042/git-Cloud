package com.ruoyi.common.reactor.exception.channel;

import com.ruoyi.common.reactor.exception.ReactorException;

/**
 * @auther: KUN
 * @date: 2023/6/23
 * @description: 事件通道异常
 */
public class EventChannelException extends ReactorException {

    public EventChannelException( String defaultMessage)
    {
        super("channel", null, null, defaultMessage);
    }

}
