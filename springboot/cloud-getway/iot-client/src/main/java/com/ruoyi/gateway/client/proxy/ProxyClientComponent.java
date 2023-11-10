package com.ruoyi.gateway.client.proxy;

import com.ruoyi.gateway.client.ClientConnectProperties;
import com.ruoyi.gateway.client.ClientProperties;
import com.ruoyi.gateway.client.TcpSocketClient;
import com.ruoyi.gateway.client.component.TcpClientComponent;
import com.ruoyi.gateway.protocol.AbstractProtocol;
import com.ruoyi.gateway.proxy.ProxyClientMessage;
import com.ruoyi.gateway.utils.UniqueIdGen;

public class ProxyClientComponent extends TcpClientComponent<ProxyClientMessage> {

    public static final String ProxyClientPrefix = "Proxy:SN:";
    public static final String DefaultProxyClient = "Default:Proxy:SN";
    public ProxyClientComponent(ClientProperties.ClientProxyConnectProperties config) {
        super(config);
        if(config.getDeviceSn() == null) {
            config.setDeviceSn(DefaultProxyClient);
        }
    }

    @Override
    public TcpSocketClient createNewClient(ClientConnectProperties config) {
        ClientProperties.ClientProxyConnectProperties clientProxy;
        if(config instanceof ClientProperties.ClientProxyConnectProperties) {
            clientProxy = (ClientProperties.ClientProxyConnectProperties) config;
            if(clientProxy.getDeviceSn() == null) {
                clientProxy.setDeviceSn(ProxyClientPrefix + UniqueIdGen.deviceSn());
            }
        } else {
            throw new IllegalArgumentException("请使用代理客户端配置对象[ClientProperties.ClientProxy]");
        }

        return new ProxyClient(this, clientProxy);
    }

    @Override
    public String getName() {
        return "代理客户端";
    }

    @Override
    public String getDesc() {
        return "用于客户端对设备的代理操作";
    }

    @Override
    public AbstractProtocol getProtocol(ProxyClientMessage message) {
        String messageId = message.getMessageId();

        return remove(messageId);
    }
}
