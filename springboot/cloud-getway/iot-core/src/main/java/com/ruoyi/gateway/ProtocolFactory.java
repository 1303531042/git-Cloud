package com.ruoyi.gateway;


import com.ruoyi.gateway.message.SocketMessage;
import com.ruoyi.gateway.protocol.AbstractProtocol;
import com.ruoyi.gateway.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>协议工厂</p>
 * Create Date By 2017-09-18
 * @author iteaj
 * @since 1.7
 */
public abstract class ProtocolFactory<T extends SocketMessage> implements IotProtocolFactory<T> {

    /*报文管理器*/
    private ProtocolTimeoutStorage delegation;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public ProtocolFactory() { }

    public ProtocolFactory(ProtocolTimeoutStorage delegation) {
        this.delegation = delegation;
    }

    @Override
    public AbstractProtocol get(String key) {
        return (AbstractProtocol) delegation.get(key);
    }

    @Override
    public AbstractProtocol add(String key, Protocol val) {
        return (AbstractProtocol) delegation.add(key, val);
    }

    @Override
    public AbstractProtocol add(String key, Protocol protocol, long timeout) {
        return (AbstractProtocol) delegation.add(key, protocol, timeout);
    }

    @Override
    public AbstractProtocol remove(String key) {
        if(key == null) return null;

        if(isExists(key)) { // 存在
            return (AbstractProtocol) delegation.remove(key);
        }

        return null;
    }

    @Override
    public boolean isExists(String key) {
        return delegation.isExists(key);
    }

    @Override
    public Object getStorage() {
        return delegation.getStorage();
    }

    public ProtocolTimeoutStorage getDelegation() {
        return delegation;
    }

    public void setDelegation(ProtocolTimeoutStorage delegation) {
        this.delegation = delegation;
    }
}
