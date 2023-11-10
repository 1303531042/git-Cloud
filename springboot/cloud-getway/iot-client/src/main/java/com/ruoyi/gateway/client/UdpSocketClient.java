package com.ruoyi.gateway.client;

import com.ruoyi.gateway.client.component.UdpClientComponent;
import com.ruoyi.gateway.client.protocol.ClientSocketProtocol;
import com.ruoyi.gateway.client.udp.UdpClientConnectProperties;
import com.ruoyi.gateway.client.udp.UdpClientMessage;
import com.ruoyi.gateway.codec.adapter.DatagramPacketDecoderAdapter;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UdpSocketClient extends SocketClient {

    public UdpSocketClient(UdpClientComponent clientComponent, UdpClientConnectProperties config) {
        super(clientComponent, config);
    }

    @Override
    protected void doInitOptions(Bootstrap bootstrap) {
        bootstrap.option(ChannelOption.SO_BROADCAST, true);
    }

    @Override
    protected Class<? extends Channel> channel() {
        return NioDatagramChannel.class;
    }

    @Override
    protected ChannelInboundHandler createProtocolDecoder() {
        return new DatagramPacketDecoderAdapter();
    }

    /**
     * 写出的报文必须是 UdpRequestProtocol 协议类型
     * @param clientProtocol
     * @return
     */
    @Override
    public ChannelFuture writeAndFlush(ClientSocketProtocol clientProtocol) {
        // 写入Udp报文 DatagramPacket
        ClientMessage message = clientProtocol.requestMessage();
        if(message instanceof UdpClientMessage) {
            UdpClientConnectProperties config = getConfig();
            ((UdpClientMessage) message).setSender(config.getSender());
            ((UdpClientMessage) message).setRecipient(config.getRecipient());

            if(message.getMessage() == null) {
                message.writeBuild();
            }

            final ByteBuf byteBuf = Unpooled.wrappedBuffer(message.getMessage());
            if(config.getRecipient() != null && config.getSender() != null) {
                clientProtocol.setPacket(new DatagramPacket(byteBuf, config.getRecipient(), config.getSender()));
            } else {
                clientProtocol.setPacket(new DatagramPacket(byteBuf, config.getRecipient()));
            }
        } else {
            throw new IllegalArgumentException("Udp协议的客户端组件必须使用报文类型[UdpClientMessage]");
        }

        return super.writeAndFlush(clientProtocol);
    }

    @Override
    public UdpClientConnectProperties getConfig() {
        return (UdpClientConnectProperties) super.getConfig();
    }
}
