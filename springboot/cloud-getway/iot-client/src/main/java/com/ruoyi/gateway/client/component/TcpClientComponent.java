package com.ruoyi.gateway.client.component;

import com.ruoyi.gateway.client.ClientConnectProperties;
import com.ruoyi.gateway.client.TcpSocketClient;
import com.ruoyi.gateway.client.ClientMessage;
import com.ruoyi.gateway.client.MultiClientManager;
import io.netty.buffer.ByteBuf;

public abstract class TcpClientComponent<M extends ClientMessage> extends SocketClientComponent<M, ByteBuf> {

    public TcpClientComponent() { }

    /**
     * @param config 默认客户端配置
     */
    public TcpClientComponent(ClientConnectProperties config) {
        super(config);
    }

    public TcpClientComponent(ClientConnectProperties config, MultiClientManager clientManager) {
        super(config, clientManager);
    }

    public abstract TcpSocketClient createNewClient(ClientConnectProperties config);
}
