package com.ruoyi.gateway.server.dtu;

import com.ruoyi.gateway.protocol.Protocol;
import com.ruoyi.gateway.server.dtu.message.DtuMessage;

public interface DtuCommonProtocol<M extends DtuMessage> extends Protocol {

    @Override
    M requestMessage();

    @Override
    M responseMessage();
}
