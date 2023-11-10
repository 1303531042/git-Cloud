package com.ruoyi.gateway.websocket;

public interface WebSocketServerMessage extends WebSocketMessage {

    HttpRequestWrapper request();

    WebSocketMessage setRequest(HttpRequestWrapper request);
}
