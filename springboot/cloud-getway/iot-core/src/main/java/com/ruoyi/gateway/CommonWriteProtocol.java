package com.ruoyi.gateway;

import com.ruoyi.gateway.business.BusinessFactory;
import com.ruoyi.gateway.protocol.AbstractProtocol;
import com.ruoyi.gateway.server.ServerSocketProtocol;
import com.ruoyi.gateway.server.ServerMessage;

/**
 * 通用写协议
 */
public class CommonWriteProtocol extends ServerSocketProtocol<ServerMessage> {

    public CommonWriteProtocol(ServerMessage writeMessage) {
        this.requestMessage = this.responseMessage = writeMessage;
    }

    @Override
    public AbstractProtocol buildRequestMessage() {
        throw new UnsupportedOperationException("不支持操作");
    }

    @Override
    public AbstractProtocol buildResponseMessage() {
        throw new UnsupportedOperationException("不支持操作");
    }

    @Override
    public AbstractProtocol exec(BusinessFactory factory) {
        throw new UnsupportedOperationException("不支持操作");
    }

    @Override
    public AbstractProtocol exec(ProtocolHandle business) {
        throw new UnsupportedOperationException("不支持操作");
    }

    @Override
    public <T> T protocolType() {
        throw new UnsupportedOperationException("不支持操作");
    }
}
