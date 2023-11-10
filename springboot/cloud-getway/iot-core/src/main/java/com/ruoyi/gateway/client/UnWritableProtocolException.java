package com.ruoyi.gateway.client;

import com.ruoyi.gateway.protocol.Protocol;
import com.ruoyi.gateway.ProtocolException;

/**
 * 协议不可写异常
 */
public class UnWritableProtocolException extends ProtocolException {

    /**
     * @see Protocol
     * @param protocol
     */
    public UnWritableProtocolException(Object protocol) {
        super(protocol);
    }

    public UnWritableProtocolException(String message, Object protocol) {
        super(message, protocol);
    }

    @Override
    public Protocol getProtocol() {
        return (Protocol) super.getProtocol();
    }
}
