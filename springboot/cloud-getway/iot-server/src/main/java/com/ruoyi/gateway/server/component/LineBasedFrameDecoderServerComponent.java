package com.ruoyi.gateway.server.component;

import com.ruoyi.gateway.codec.adapter.LineBasedFrameMessageDecoderAdapter;
import com.ruoyi.gateway.config.ConnectProperties;
import com.ruoyi.gateway.server.ServerMessage;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * 基于换行符的解码器组件
 * 支持 "\n" and "\r\n".
 * @see LineBasedFrameDecoder
 * @see TcpServerComponent
 */
public abstract class LineBasedFrameDecoderServerComponent<M extends ServerMessage> extends TcpDecoderServerComponent<M> {

    private int maxLength;
    private boolean failFast;
    private boolean stripDelimiter;

    public LineBasedFrameDecoderServerComponent(ConnectProperties connectProperties, int maxLength) {
        this(connectProperties, maxLength, true, false);
    }

    public LineBasedFrameDecoderServerComponent(ConnectProperties connectProperties, int maxLength, boolean stripDelimiter, boolean failFast) {
        super(connectProperties);
        this.failFast = failFast;
        this.maxLength = maxLength;
        this.stripDelimiter = stripDelimiter;
    }

    @Override
    public ChannelInboundHandlerAdapter getMessageDecoder() {
        return new LineBasedFrameMessageDecoderAdapter(this.maxLength, this.stripDelimiter, this.failFast);
    }

}
