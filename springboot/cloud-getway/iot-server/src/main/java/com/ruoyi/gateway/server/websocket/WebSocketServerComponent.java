package com.ruoyi.gateway.server.websocket;

import com.ruoyi.gateway.websocket.WebSocketComponent;
import com.ruoyi.gateway.websocket.WebSocketServerMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

public interface WebSocketServerComponent<M extends WebSocketServerMessage> extends WebSocketComponent<M> {

    WebSocketServerHandshaker createServerHandShaker(ChannelHandlerContext ctx, HttpRequest req);
}
