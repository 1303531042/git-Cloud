package com.ruoyi.gateway.codec.adapter;

import com.ruoyi.gateway.message.SocketMessage;
import com.ruoyi.gateway.codec.SocketMessageDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 * create time: 2021/8/13
 *  固定字段解码适配器
 * @author iteaj
 * @since 1.0
 */
public class FixedLengthFrameDecoderAdapter extends FixedLengthFrameDecoder implements SocketMessageDecoderDelegation<ByteBuf> {

    private SocketMessageDecoder delegation;

    /**
     * Creates AbstractQueryTemplateFluxWrapper new instance.
     *
     * @param frameLength the length of the frame
     */
    public FixedLengthFrameDecoderAdapter(int frameLength) {
        super(frameLength);
    }


    @Override
    public SocketMessage decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        return this.proxy(ctx, (ByteBuf) super.decode(ctx, in));
    }

    @Override
    public SocketMessageDecoder<ByteBuf> getDelegation() {
        return this.delegation;
    }

    @Override
    public SocketMessageDecoderDelegation setDelegation(SocketMessageDecoder<ByteBuf> delegation) {
        this.delegation = delegation;
        return this;
    }
}
