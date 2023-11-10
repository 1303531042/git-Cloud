package com.ruoyi.common.reactor.exception.channel;

/**
 * @auther: KUN
 * @date: 2023/6/23
 * @description: channel id 重复异常
 */
public class EventChannelIdException extends  EventChannelException{

    public EventChannelIdException( String defaultMessage) {
        super( defaultMessage);
    }
}
