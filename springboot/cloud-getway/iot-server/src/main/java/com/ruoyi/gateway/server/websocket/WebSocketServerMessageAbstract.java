package com.ruoyi.gateway.server.websocket;

import com.ruoyi.gateway.server.ServerMessage;
import com.ruoyi.gateway.websocket.HttpRequestWrapper;
import com.ruoyi.gateway.websocket.WebSocketFrameType;
import com.ruoyi.gateway.websocket.WebSocketMessage;
import com.ruoyi.gateway.websocket.WebSocketServerMessage;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

import java.util.Optional;

public abstract class WebSocketServerMessageAbstract extends ServerMessage implements WebSocketServerMessage {

    private HttpRequestWrapper request;
    private WebSocketFrameType frameType;

    public WebSocketServerMessageAbstract(byte[] message) {
        super(message);
    }

    public WebSocketServerMessageAbstract(MessageHead head) {
        super(head);
    }

    public WebSocketServerMessageAbstract(MessageHead head, MessageBody body) {
        super(head, body);
    }

    @Override
    public String uri() {
        return request().getRawPath();
    }

    @Override
    public WebSocketVersion version() {
        return request().getVersion();
    }

    @Override
    public Optional<String> getHeader(String key) {
        return Optional.ofNullable(request().headers().get(key));
    }

    @Override
    public Optional<String> getQueryParam(String key) {
        return request().getQueryParam(key);
    }

    @Override
    public WebSocketFrameType frameType() {
        return this.frameType;
    }

    public WebSocketServerMessageAbstract setFrameType(WebSocketFrameType frameType) {
        this.frameType = frameType;
        return this;
    }

    public WebSocketFrameType getFrameType() {
        return frameType;
    }

    @Override
    public HttpRequestWrapper request() {
        return this.request;
    }

    @Override
    public WebSocketMessage setRequest(HttpRequestWrapper request) {
        this.request = request;
        return this;
    }
}
