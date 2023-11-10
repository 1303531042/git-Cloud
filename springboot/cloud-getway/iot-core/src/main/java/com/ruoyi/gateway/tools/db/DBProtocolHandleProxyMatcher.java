package com.ruoyi.gateway.tools.db;

import com.ruoyi.gateway.handle.proxy.ProtocolHandleInvocationHandler;
import com.ruoyi.gateway.handle.proxy.ProtocolHandleProxy;
import com.ruoyi.gateway.handle.proxy.ProtocolHandleProxyMatcher;

/**
 * 数据存储代理处理器
 */
public interface DBProtocolHandleProxyMatcher extends ProtocolHandleProxyMatcher {

    DBManager getDbManager();

    Class<? extends ProtocolHandleProxy> getProxyClass();

    @Override
    default ProtocolHandleInvocationHandler invocationHandler(Object target, ProtocolHandleProxy handleProxy) {
        return new ProtocolHandleInvocationHandler(target, handleProxy) {

            @Override
            protected Class<? extends ProtocolHandleProxy> getProxyClass() {
                return DBProtocolHandleProxyMatcher.this.getProxyClass();
            }

            @Override
            protected Object proxyHandle(Object value, Object proxy) {
                getDbManager().execute(value, getOriTarget());
                return value;
            }
        };
    }
}
