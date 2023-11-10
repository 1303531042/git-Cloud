package com.ruoyi.gateway.client.protocol;

import com.ruoyi.gateway.FreeProtocolHandle;

public interface ClientProtocolCallHandle extends FreeProtocolHandle<ClientInitiativeProtocol> {

    @Override
    default Object handle(ClientInitiativeProtocol protocol) {
        this.doHandle(protocol);
        return null;
    }

    void doHandle(ClientInitiativeProtocol protocol);
}
