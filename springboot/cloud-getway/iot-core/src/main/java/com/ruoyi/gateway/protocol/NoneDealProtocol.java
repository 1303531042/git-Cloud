package com.ruoyi.gateway.protocol;

import com.ruoyi.gateway.message.SocketMessage;
import com.ruoyi.gateway.server.ServerMessage;

/**
 * 此协议将不做任何处理
 */
public class NoneDealProtocol extends AbstractProtocol<SocketMessage> {

    private static NoneDealProtocol instance;

    protected NoneDealProtocol(ServerMessage message) {
        this.requestMessage = message;
    }

    public static NoneDealProtocol getInstance(ServerMessage message) {
        if(instance != null) return instance;

        instance = new NoneDealProtocol(message);

        return instance;
    }


    @Override
    public CommonProtocolType protocolType() {
        return CommonProtocolType.NoneMap;
    }

    @Override
    public AbstractProtocol buildRequestMessage() {
        throw new UnsupportedOperationException("不支持此操作");
    }

    @Override
    public AbstractProtocol buildResponseMessage() {
        throw new UnsupportedOperationException("不支持此操作");
    }
}
