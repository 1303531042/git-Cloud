package com.ruoyi.gateway.server;

import com.ruoyi.gateway.protocol.Protocol;
import com.ruoyi.gateway.business.BusinessFactory;

public class ServerProtocolHandleFactory extends BusinessFactory<ServerProtocolHandle> {

    @Override
    protected Class<? extends Protocol> getProtocolClass(ServerProtocolHandle item) {
        return item.protocolClass();
    }

    @Override
    protected Class<ServerProtocolHandle> getServiceClass() {
        return ServerProtocolHandle.class;
    }
}
