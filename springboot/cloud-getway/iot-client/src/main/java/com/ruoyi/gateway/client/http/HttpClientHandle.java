package com.ruoyi.gateway.client.http;

import com.ruoyi.gateway.client.ClientProtocolHandle;

/**
 * 基于http实现的协议的处理器
 * @param <T>
 */
public interface HttpClientHandle<T extends ClientHttpProtocol> extends ClientProtocolHandle<T> {

}
