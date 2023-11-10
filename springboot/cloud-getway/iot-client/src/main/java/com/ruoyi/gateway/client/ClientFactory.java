package com.ruoyi.gateway.client;

import com.ruoyi.gateway.config.ConnectProperties;

/**
 * 创建客户端
 */
public interface ClientFactory {

    IotClient createNewClient(ConnectProperties config);
}
