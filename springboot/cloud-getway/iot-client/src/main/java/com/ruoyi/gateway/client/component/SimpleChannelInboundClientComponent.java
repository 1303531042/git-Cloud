package com.ruoyi.gateway.client.component;

import com.ruoyi.gateway.message.SocketMessage;
import com.ruoyi.gateway.client.ClientConnectProperties;
import com.ruoyi.gateway.client.ClientMessage;
import com.ruoyi.gateway.client.MultiClientManager;
import com.ruoyi.gateway.client.TcpSocketClient;
import com.ruoyi.gateway.client.codec.SimpleChannelInboundClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public abstract class SimpleChannelInboundClientComponent<M extends ClientMessage> extends TcpClientComponent<M>{

    public SimpleChannelInboundClientComponent() { }

    public SimpleChannelInboundClientComponent(ClientConnectProperties config) {
        super(config);
    }

    public SimpleChannelInboundClientComponent(ClientConnectProperties config, MultiClientManager clientManager) {
        super(config, clientManager);
    }

    @Override
    public TcpSocketClient createNewClient(ClientConnectProperties config) {
        return new SimpleChannelInboundClient(this, config);
    }

    /**
     * @see ByteBuf#release()  需要自行释放
     * @param ctx
     * @param decode
     * @return
     */
    @Override
    public abstract SocketMessage doTcpDecode(ChannelHandlerContext ctx, ByteBuf decode);
}
