package com.ruoyi.gateway.server.protocol;

import com.ruoyi.gateway.FreeProtocolHandle;

public interface ServerProtocolCallHandle extends FreeProtocolHandle<ServerInitiativeProtocol> {

    @Override
    default Object handle(ServerInitiativeProtocol protocol) {
        this.doHandle(protocol);
        return null;
    }

    void doHandle(ServerInitiativeProtocol protocol);
}
