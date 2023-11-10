package com.ruoyi.gateway;

import com.ruoyi.gateway.message.SocketMessage;
import com.ruoyi.gateway.protocol.AbstractProtocol;

public class ProtocolFactoryDelegation<M extends SocketMessage> extends ProtocolFactory<M> {

    private IotProtocolFactory iotProtocolFactory;

    public ProtocolFactoryDelegation(IotProtocolFactory iotProtocolFactory,ProtocolTimeoutStorage delegation) {
        super(delegation);
        this.iotProtocolFactory = iotProtocolFactory;
    }

    @Override
    public AbstractProtocol getProtocol(M message) {
        return iotProtocolFactory.getProtocol(message);
    }
}
