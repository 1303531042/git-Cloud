package com.ruoyi.gateway.server.component;

import com.ruoyi.gateway.codec.adapter.ByteToMessageDecoderAdapter;
import com.ruoyi.gateway.config.ConnectProperties;
import com.ruoyi.gateway.server.ServerMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * create time: 2021/2/21
 *  适配{@link ByteToMessageDecoder}解码器到服务组件{@link TcpServerComponent}
 * @author iteaj
 * @since 1.0
 *
 * 实现 解码器实例
 */
public abstract class ByteToMessageDecoderServerComponent<M extends ServerMessage> extends TcpDecoderServerComponent<M> {

    public ByteToMessageDecoderServerComponent(ConnectProperties connectProperties) {
        super(connectProperties);
    }

    @Override
    public ChannelInboundHandlerAdapter getMessageDecoder() {
        return new ByteToMessageDecoderAdapter(this);
    }

    /**
     * 自定义解码
     * @param ctx
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public abstract M doTcpDecode(ChannelHandlerContext ctx, ByteBuf in);
}
