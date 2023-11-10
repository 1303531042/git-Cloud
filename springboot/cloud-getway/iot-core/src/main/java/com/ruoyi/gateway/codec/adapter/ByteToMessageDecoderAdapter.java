package com.ruoyi.gateway.codec.adapter;

import com.ruoyi.gateway.message.SocketMessage;
import com.ruoyi.gateway.codec.SocketMessageDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ByteToMessageDecoderAdapter extends ByteToMessageDecoder implements SocketMessageDecoderDelegation<ByteBuf> {

    private SocketMessageDecoder delegation;

    public ByteToMessageDecoderAdapter(SocketMessageDecoder delegation) {
        this.delegation = delegation;
    }

    @Override
    protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        SocketMessage message = proxy(ctx, in);
        if (message != null) {
            out.add(message);
        }
    }

    @Override
    public SocketMessageDecoder getDelegation() {
        return this.delegation;
    }

    @Override
    public ByteToMessageDecoderAdapter setDelegation(SocketMessageDecoder<ByteBuf> delegation) {
        this.delegation = delegation;
        return this;
    }
}
