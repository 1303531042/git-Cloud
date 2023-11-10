package com.ruoyi.gateway.client.websocket.impl;

import com.ruoyi.gateway.message.Message;
import com.ruoyi.gateway.client.ClientProtocolHandle;
import com.ruoyi.gateway.client.websocket.WebSocketClientListener;
import com.ruoyi.gateway.websocket.WebSocketConnectHead;
import com.ruoyi.gateway.websocket.WebSocketException;
import com.ruoyi.gateway.websocket.WebSocketFrameType;

public class DefaultWebSocketClientProtocolHandle implements ClientProtocolHandle<DefaultWebSocketClientProtocol> {

    private DefaultWebSocketClientComponent component;

    public DefaultWebSocketClientProtocolHandle(DefaultWebSocketClientComponent component) {
        this.component = component;
    }

    @Override
    public Object handle(DefaultWebSocketClientProtocol protocol) {

        DefaultWebSocketClientMessage requestMessage = protocol.requestMessage();
        WebSocketClientListener listener = requestMessage.getProperties().getListener();
        if(listener != null) {
            Message.MessageHead head = requestMessage.getHead();
            if(head instanceof WebSocketConnectHead) {
                listener.onConnect(protocol);
            } else {
                WebSocketFrameType type = requestMessage.frameType();
                switch (type) {
                    case Text: listener.onText(protocol); break;
                    case Close: listener.onClose(protocol); break;
                    case Binary: listener.onBinary(protocol); break;
                    default: throw new WebSocketException("不支持的事件["+type+"]");
                }
            }
        }

        return null;
    }
}
