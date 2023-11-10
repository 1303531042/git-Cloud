package com.ruoyi.gateway.client;

import com.ruoyi.gateway.protocol.Protocol;
import com.ruoyi.gateway.business.BusinessFactory;

public class ClientProtocolHandleFactory extends BusinessFactory<ClientProtocolHandle> {

    @Override
    protected Class<? extends Protocol> getProtocolClass(ClientProtocolHandle item) {
        return item.protocolClass();
    }

    @Override
    protected Class<ClientProtocolHandle> getServiceClass() {
        return ClientProtocolHandle.class;
    }
}
