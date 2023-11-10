package com.ruoyi.gateway.client;

import com.ruoyi.gateway.client.component.TcpClientComponent;
import com.ruoyi.gateway.codec.SocketMessageDecoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * create time: 2021/8/6
 *
 * @author iteaj
 * @since 1.0
 */
public abstract class TcpSocketClient extends SocketClient {

    public TcpSocketClient(TcpClientComponent clientComponent, ClientConnectProperties config) {
        super(clientComponent, config);
    }

    protected Class<? extends Channel> channel() {
        return NioSocketChannel.class;
    }

    @Override
    public TcpClientComponent getClientComponent() {
        return (TcpClientComponent) super.getClientComponent();
    }

    /**
     * @see SocketMessageDecoder 基于netty的常用tcp解码器适配
     * @return
     */
    @Override
    protected abstract ChannelInboundHandler createProtocolDecoder();
}
