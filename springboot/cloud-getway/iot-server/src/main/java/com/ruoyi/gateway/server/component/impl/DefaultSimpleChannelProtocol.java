package com.ruoyi.gateway.server.component.impl;

import com.ruoyi.gateway.protocol.AbstractProtocol;
import com.ruoyi.gateway.ProtocolHandle;
import com.ruoyi.gateway.business.BusinessFactory;
import com.ruoyi.gateway.server.ServerSocketProtocol;

public class DefaultSimpleChannelProtocol extends ServerSocketProtocol<DefaultSimpleServerMessage> {

    @Override
    public AbstractProtocol buildRequestMessage() {
        return null;
    }

    @Override
    public AbstractProtocol buildResponseMessage() {
        return null;
    }

    @Override
    public AbstractProtocol exec(BusinessFactory factory) {
        return null;
    }

    @Override
    public AbstractProtocol exec(ProtocolHandle handle) {
        return null;
    }

    @Override
    public <T> T protocolType() {
        return null;
    }
}
