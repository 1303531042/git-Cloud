package com.ruoyi.gateway.websocket;

import com.ruoyi.gateway.ProtocolException;

public class WebSocketException extends ProtocolException {

    public WebSocketException(String message) {
        super(message);
    }

    public WebSocketException(String message, Object protocol) {
        super(message, protocol);
    }

    public WebSocketException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebSocketException(Throwable cause, Object protocol) {
        super(cause, protocol);
    }

    public WebSocketException(String message, Throwable cause, Object protocol) {
        super(message, cause, protocol);
    }
}
