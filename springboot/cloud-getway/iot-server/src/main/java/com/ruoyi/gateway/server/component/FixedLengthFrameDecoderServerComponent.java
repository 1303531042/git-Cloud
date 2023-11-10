package com.ruoyi.gateway.server.component;

import com.ruoyi.gateway.codec.adapter.FixedLengthFrameDecoderAdapter;
import com.ruoyi.gateway.config.ConnectProperties;
import com.ruoyi.gateway.server.ServerMessage;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 固定长度解码器组件
 * @param <M>
 */
public abstract class FixedLengthFrameDecoderServerComponent<M extends ServerMessage> extends TcpDecoderServerComponent<M> {

    private int frameLength;

    public FixedLengthFrameDecoderServerComponent(ConnectProperties connectProperties, int frameLength) {
        super(connectProperties);
        this.frameLength = frameLength;
    }

    @Override
    public ChannelInboundHandlerAdapter getMessageDecoder() {
        return new FixedLengthFrameDecoderAdapter(this.frameLength);
    }
}
