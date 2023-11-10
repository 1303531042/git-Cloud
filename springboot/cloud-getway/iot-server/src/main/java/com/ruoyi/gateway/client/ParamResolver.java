package com.ruoyi.gateway.client;

import com.ruoyi.gateway.proxy.ProxyClientMessage;
import com.ruoyi.gateway.proxy.ProxyServerMessage;

/**
 * create time: 2021/3/4
 *  参数解析器, 非线程安全
 * @author iteaj
 * @since 1.0
 */
public interface ParamResolver {

    String getDeviceSn(ProxyClientMessage message);

    String getTradeType(ProxyClientMessage message);

    Object resolver(String name, Class type, ProxyServerMessage message);
}
