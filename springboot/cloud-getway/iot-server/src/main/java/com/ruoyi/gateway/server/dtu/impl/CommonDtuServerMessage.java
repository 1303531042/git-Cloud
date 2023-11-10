package com.ruoyi.gateway.server.dtu.impl;

import com.ruoyi.gateway.message.DefaultMessageHead;
import com.ruoyi.gateway.server.dtu.DtuCommonProtocolType;
import com.ruoyi.gateway.server.dtu.message.DtuServerMessageAbstract;

public class CommonDtuServerMessage extends DtuServerMessageAbstract {

    public CommonDtuServerMessage(byte[] message) {
        super(message);
    }

    public CommonDtuServerMessage(MessageHead head) {
        super(head);
    }

    public CommonDtuServerMessage(MessageHead head, MessageBody body) {
        super(head, body);
    }

    @Override
    protected MessageHead doBuild(byte[] message, String equipCode) {
        return new DefaultMessageHead(equipCode, equipCode, DtuCommonProtocolType.COMMON);
    }
}
