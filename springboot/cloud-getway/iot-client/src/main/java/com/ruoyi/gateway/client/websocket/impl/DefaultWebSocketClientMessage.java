package com.ruoyi.gateway.client.websocket.impl;

import com.ruoyi.gateway.client.websocket.WebSocketClientMessage;
import com.ruoyi.gateway.message.DefaultMessageHead;
import com.ruoyi.gateway.websocket.WebSocketCloseHead;
import com.ruoyi.gateway.websocket.WebSocketFrameType;
import com.ruoyi.gateway.websocket.WebSocketProtocolType;

public class DefaultWebSocketClientMessage extends WebSocketClientMessage {

    private WebSocketFrameType frameType;

    public DefaultWebSocketClientMessage(byte[] message) {
        super(message);
    }

    public DefaultWebSocketClientMessage(MessageHead head) {
        super(head);
    }

    public DefaultWebSocketClientMessage(MessageHead head, MessageBody body) {
        super(head, body);
    }

    @Override
    protected MessageHead doBuild(byte[] message) {
        if(frameType() == WebSocketFrameType.Close) {
            return new WebSocketCloseHead(getChannelId());
        }

        return new DefaultMessageHead(getChannelId(), null, WebSocketProtocolType.Default_Client);
    }

    @Override
    public WebSocketFrameType frameType() {
        return this.frameType;
    }

    @Override
    public DefaultWebSocketClientMessage setFrameType(WebSocketFrameType frameType) {
        this.frameType = frameType;
        return this;
    }
}
