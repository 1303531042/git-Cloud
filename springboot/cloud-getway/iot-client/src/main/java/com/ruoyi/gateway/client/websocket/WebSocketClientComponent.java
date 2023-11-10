package com.ruoyi.gateway.client.websocket;

import com.ruoyi.gateway.client.codec.WebSocketClient;
import com.ruoyi.gateway.websocket.WebSocketComponent;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;

public interface WebSocketClientComponent<M extends WebSocketClientMessage> extends WebSocketComponent<M> {

    WebSocketClientHandshaker createClientHandShaker(WebSocketClient client);
}
