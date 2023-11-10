package com.ruoyi.gateway.server.component.impl;

import com.ruoyi.gateway.message.DefaultMessageHead;
import com.ruoyi.gateway.server.ServerMessage;

public class DefaultSimpleServerMessage extends ServerMessage {

    public DefaultSimpleServerMessage(byte[] message) {
        super(message);
    }

    public DefaultSimpleServerMessage(MessageHead head) {
        super(head);
    }

    public DefaultSimpleServerMessage(MessageHead head, MessageBody body) {
        super(head, body);
    }

    @Override
    protected MessageHead doBuild(byte[] message) {
        return new DefaultMessageHead(getChannelId(), getChannelId(), null);
    }
}
