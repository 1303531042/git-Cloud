package com.ruoyi.gateway.websocket;

import com.ruoyi.gateway.message.DefaultMessageHead;

public class WebSocketCloseHead extends DefaultMessageHead {

    public WebSocketCloseHead(String equipCode) {
        super(equipCode, null, WebSocketProtocolType.Close);
    }
}
