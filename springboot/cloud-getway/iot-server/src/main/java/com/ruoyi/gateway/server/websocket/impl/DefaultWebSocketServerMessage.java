package com.ruoyi.gateway.server.websocket.impl;

import com.ruoyi.gateway.message.DefaultMessageHead;
import com.ruoyi.gateway.server.websocket.WebSocketServerMessageAbstract;
import com.ruoyi.gateway.websocket.WebSocketCloseHead;
import com.ruoyi.gateway.websocket.WebSocketFrameType;
import com.ruoyi.gateway.websocket.WebSocketProtocolType;

public class DefaultWebSocketServerMessage extends WebSocketServerMessageAbstract {

    public DefaultWebSocketServerMessage(byte[] message) {
        super(message);
    }

    public DefaultWebSocketServerMessage(MessageHead head) {
        super(head);
    }

    public DefaultWebSocketServerMessage(MessageHead head, MessageBody body) {
        super(head, body);
    }

    public DefaultWebSocketServerMessage(MessageHead head, WebSocketFrameType frameType) {
        super(head);
        this.setFrameType(frameType);
    }

    public DefaultWebSocketServerMessage(MessageHead head, MessageBody body, WebSocketFrameType frameType) {
        super(head, body);
        this.setFrameType(frameType);
    }

    @Override
    protected MessageHead doBuild(byte[] message) {
        if(frameType() == WebSocketFrameType.Close) {
            return new WebSocketCloseHead(getChannelId());
        }

        return new DefaultMessageHead(getChannelId(), null, WebSocketProtocolType.Default_Server);
    }

    @Override
    public DefaultWebSocketServerMessage setFrameType(WebSocketFrameType frameType) {
        return (DefaultWebSocketServerMessage) super.setFrameType(frameType);
    }
}
