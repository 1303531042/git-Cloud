package com.ruoyi.gateway.codec.adapter;

import com.ruoyi.gateway.message.SocketMessage;
import com.ruoyi.gateway.codec.SocketMessageDecoder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

@ChannelHandler.Sharable
public class DatagramPacketDecoderAdapter extends SimpleChannelInboundHandler<DatagramPacket> implements SocketMessageDecoderDelegation<DatagramPacket> {

    private SocketMessageDecoder delegation;

    public DatagramPacketDecoderAdapter() {
        super(false);
    }

    @Override
    public SocketMessageDecoder<DatagramPacket> getDelegation() {
        return this.delegation;
    }

    @Override
    public SocketMessageDecoderDelegation setDelegation(SocketMessageDecoder<DatagramPacket> delegation) {
        this.delegation = delegation;
        return this;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        final SocketMessage proxy = this.proxy(ctx, msg);
        if(proxy != null) {
            ctx.fireChannelRead(proxy);
        }
    }
}
