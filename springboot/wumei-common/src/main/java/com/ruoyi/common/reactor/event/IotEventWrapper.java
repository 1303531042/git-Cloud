package com.ruoyi.common.reactor.event;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther: KUN
 * @date: 2023/6/25
 * @description: 事件包装
 */
@Data
public class IotEventWrapper {
    /**
     * 事件
     */
    private IotEvent iotEvent;

    /**
     * 事件订阅的channel ID ops
     */
    private AtomicInteger channelIDOps;
}
