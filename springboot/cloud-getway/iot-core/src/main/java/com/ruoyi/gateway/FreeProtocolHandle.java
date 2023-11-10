package com.ruoyi.gateway;

import com.ruoyi.gateway.protocol.Protocol;

/**
 * 自定义协议处理器
 */
public interface FreeProtocolHandle<T extends Protocol> extends ProtocolHandle<T> {

    @Override
    Object handle(T protocol);
}
