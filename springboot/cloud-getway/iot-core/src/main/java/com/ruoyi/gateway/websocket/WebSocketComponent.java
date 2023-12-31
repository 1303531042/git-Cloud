package com.ruoyi.gateway.websocket;

import com.ruoyi.gateway.FrameworkComponent;

/**
 * 用于实现Websocket功能的组件
 */
public interface WebSocketComponent<M extends WebSocketMessage> extends FrameworkComponent {

    @Override
    Class<? extends M> getMessageClass();
}
