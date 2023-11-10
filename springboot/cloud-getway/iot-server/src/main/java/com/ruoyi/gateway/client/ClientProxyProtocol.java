package com.ruoyi.gateway.client;

import com.ruoyi.gateway.protocol.ProtocolType;
import com.ruoyi.gateway.proxy.ProxyServerMessage;
import com.ruoyi.gateway.server.protocol.ServerInitiativeProtocol;

import java.io.IOException;

public class ClientProxyProtocol extends ServerInitiativeProtocol<ProxyServerMessage> {

    @Override
    protected ProxyServerMessage doBuildRequestMessage() throws IOException {
        return null;
    }

    @Override
    protected void doBuildResponseMessage(ProxyServerMessage message) {

    }

    @Override
    public ProtocolType protocolType() {
        return null;
    }
}
