package com.ruoyi.gateway.client;

import com.ruoyi.gateway.protocol.Protocol;
import com.ruoyi.gateway.ProtocolException;

/**
 * create time: 2021/8/8
 *  客户端协议
 * @author iteaj
 * @since 1.0
 */
public interface ClientProtocol<M extends ClientMessage> extends Protocol {

    @Override
    M requestMessage();

    @Override
    M responseMessage();

    /**
     * 获取组件默认的客户端
     * @return
     */
    IotClient getIotClient() throws ProtocolException;
}
