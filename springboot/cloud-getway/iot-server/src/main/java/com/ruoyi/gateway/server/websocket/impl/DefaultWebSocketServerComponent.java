package com.ruoyi.gateway.server.websocket.impl;

import com.ruoyi.gateway.protocol.AbstractProtocol;
import com.ruoyi.gateway.config.ConnectProperties;
import com.ruoyi.gateway.server.websocket.WebSocketServerComponentAbstract;

/**
 * 默认WebSocket服务端组件
 */
public class DefaultWebSocketServerComponent extends WebSocketServerComponentAbstract<DefaultWebSocketServerMessage> {

    public DefaultWebSocketServerComponent(ConnectProperties connectProperties) {
        super(connectProperties);
    }

    @Override
    public String getDesc() {
        return "websocket服务端默认实现";
    }

    @Override
    public Class<DefaultWebSocketServerMessage> getMessageClass() {
        return DefaultWebSocketServerMessage.class;
    }

    @Override
    public DefaultWebSocketServerMessage createMessage(byte[] message) {
        return new DefaultWebSocketServerMessage(message);
    }

    @Override
    public AbstractProtocol getProtocol(DefaultWebSocketServerMessage message) {
        return new DefaultWebSocketServerProtocol(message);
    }

    @Override
    public String getName() {
        return "websocket(Server)";
    }
}
