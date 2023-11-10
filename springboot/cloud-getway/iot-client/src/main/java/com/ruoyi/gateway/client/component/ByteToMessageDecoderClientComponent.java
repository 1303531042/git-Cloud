package com.ruoyi.gateway.client.component;

import com.ruoyi.gateway.message.SocketMessage;
import com.ruoyi.gateway.client.ClientConnectProperties;
import com.ruoyi.gateway.client.ClientMessage;
import com.ruoyi.gateway.client.MultiClientManager;
import com.ruoyi.gateway.client.TcpSocketClient;
import com.ruoyi.gateway.client.codec.ByteToMessageDecoderClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public abstract class ByteToMessageDecoderClientComponent<M extends ClientMessage> extends TcpClientComponent<M>{

    public ByteToMessageDecoderClientComponent(ClientConnectProperties config) {
        super(config);
    }

    public ByteToMessageDecoderClientComponent(ClientConnectProperties config, MultiClientManager clientManager) {
        super(config, clientManager);
    }

    @Override
    public TcpSocketClient createNewClient(ClientConnectProperties config) {
        return new ByteToMessageDecoderClient(this, config);
    }

    @Override
    public abstract SocketMessage doTcpDecode(ChannelHandlerContext ctx, ByteBuf decode);
}
