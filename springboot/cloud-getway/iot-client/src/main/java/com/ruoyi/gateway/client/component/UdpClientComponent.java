package com.ruoyi.gateway.client.component;

import com.ruoyi.gateway.client.ClientConnectProperties;
import com.ruoyi.gateway.client.MultiClientManager;
import com.ruoyi.gateway.client.SocketClient;
import com.ruoyi.gateway.client.UdpSocketClient;
import com.ruoyi.gateway.client.udp.UdpClientConnectProperties;
import com.ruoyi.gateway.client.udp.UdpClientMessage;
import io.netty.channel.socket.DatagramPacket;

/**
 * 基于udp实现的客户端
 */
public abstract class UdpClientComponent<M extends UdpClientMessage> extends SocketClientComponent<M, DatagramPacket> {

    public UdpClientComponent() { }

    public UdpClientComponent(UdpClientConnectProperties config) {
        super(config);
    }

    public UdpClientComponent(UdpClientConnectProperties config, MultiClientManager clientManager) {
        super(config, clientManager);
    }

    public UdpSocketClient createNewClient(ClientConnectProperties config) {
        if(!(config instanceof UdpClientConnectProperties)) {
            throw new IllegalArgumentException("Udp协议的客户端组件只支持配置类型[UdpClientConnectProperties]");
        }

        return new UdpSocketClient(this, (UdpClientConnectProperties) config);
    }

    @Override
    public SocketClient createNewClientAndConnect(ClientConnectProperties config) {
        return super.createNewClientAndConnect(config);
    }
}
