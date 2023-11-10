package com.ruoyi.gateway.server.component;

import com.ruoyi.gateway.IotProtocolFactory;
import com.ruoyi.gateway.ProtocolFactoryDelegation;
import com.ruoyi.gateway.config.ConnectProperties;
import com.ruoyi.gateway.protocol.AbstractProtocol;
import com.ruoyi.gateway.protocol.Protocol;
import com.ruoyi.gateway.server.ServerMessage;
import com.ruoyi.gateway.server.codec.DeviceProtocolEncoder;
import io.netty.channel.ChannelPipeline;

/**
 * create time: 2021/2/20
 *  做了 factory 实例创建  静态代理 factory
 * @author iteaj
 * @since 1.0
 */
public abstract class TcpDecoderServerComponent<M extends ServerMessage> extends TcpServerComponent<M> implements IotProtocolFactory<M> {

    private ProtocolFactoryDelegation delegation;

    public TcpDecoderServerComponent(ConnectProperties connectProperties) {
        super(connectProperties);
        this.delegation = new ProtocolFactoryDelegation(this, protocolTimeoutStorage());
    }

    @Override
    public abstract String getName();

    @Override
    protected IotProtocolFactory createProtocolFactory() {
        return this;
    }

    @Override
    public void init(Object... args) {
        this.doInitChannel((ChannelPipeline) args[0]);
    }

    @Override
    public AbstractProtocol get(String key) {
        return this.delegation.get(key);
    }

    @Override
    public AbstractProtocol add(String key, Protocol val) {
        return this.delegation.add(key, val);
    }

    @Override
    public AbstractProtocol add(String key, Protocol protocol, long timeout) {
        return this.delegation.add(key, protocol, timeout);
    }

    @Override
    public AbstractProtocol remove(String key) {
        return this.delegation.remove(key);
    }

    @Override
    public boolean isExists(String key) {
        return this.delegation.isExists(key);
    }

    @Override
    public Object getStorage() {
        return this.delegation.getStorage();
    }

}
