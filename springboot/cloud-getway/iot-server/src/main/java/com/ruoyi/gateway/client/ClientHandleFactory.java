package com.ruoyi.gateway.client;

import com.ruoyi.gateway.client.handle.MethodMeta;
import com.ruoyi.gateway.proxy.ProxyServerMessage;

/**
 * create time: 2021/3/4
 *
 * @author iteaj
 * @since 1.0
 */
public interface ClientHandleFactory {

    MethodMeta getHandle(String tradeType);

    ParamResolver getResolver(Class<? extends ParamResolver> resolver);

    <T> T getRelation(ProxyServerMessage message);
}
