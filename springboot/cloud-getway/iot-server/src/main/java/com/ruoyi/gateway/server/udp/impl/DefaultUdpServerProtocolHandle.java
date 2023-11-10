package com.ruoyi.gateway.server.udp.impl;

import com.ruoyi.gateway.server.ServerProtocolHandle;

public interface DefaultUdpServerProtocolHandle extends ServerProtocolHandle<DefaultUdpServerProtocol> {

    @Override
    Object handle(DefaultUdpServerProtocol protocol);

    @Override
    default Class<DefaultUdpServerProtocol> protocolClass() {
        return DefaultUdpServerProtocol.class;
    }
}
