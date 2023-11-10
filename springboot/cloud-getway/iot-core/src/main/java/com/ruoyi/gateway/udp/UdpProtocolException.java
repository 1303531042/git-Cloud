package com.ruoyi.gateway.udp;

import com.ruoyi.gateway.ProtocolException;

public class UdpProtocolException extends ProtocolException {

    public UdpProtocolException(String message) {
        super(message);
    }

    public UdpProtocolException(String message, Object protocol) {
        super(message, protocol);
    }

    public UdpProtocolException(Throwable cause, Object protocol) {
        super(cause, protocol);
    }

    public UdpProtocolException(String message, Throwable cause, Object protocol) {
        super(message, cause, protocol);
    }
}
