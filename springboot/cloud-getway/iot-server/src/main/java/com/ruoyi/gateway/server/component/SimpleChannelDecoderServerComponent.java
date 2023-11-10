package com.ruoyi.gateway.server.component;

import com.ruoyi.gateway.codec.adapter.SimpleChannelDecoderAdapter;
import com.ruoyi.gateway.config.ConnectProperties;
import com.ruoyi.gateway.server.ServerMessage;

public abstract class SimpleChannelDecoderServerComponent<M extends ServerMessage> extends TcpDecoderServerComponent<M> {

    public SimpleChannelDecoderServerComponent(ConnectProperties connectProperties) {
        super(connectProperties);
    }

    @Override
    public SimpleChannelDecoderAdapter getMessageDecoder() {
        return new SimpleChannelDecoderAdapter(this);
    }

}
