package com.ruoyi.gateway.server.dtu.impl;

import com.ruoyi.gateway.protocol.AbstractProtocol;
import com.ruoyi.gateway.config.ConnectProperties;
import com.ruoyi.gateway.server.dtu.DtuMessageAware;
import com.ruoyi.gateway.server.dtu.DtuDecoderServerComponent;

/**
 * 通用的dtu服务组件
 */
public class CommonDtuServerComponent extends DtuDecoderServerComponent<CommonDtuServerMessage> {

    public CommonDtuServerComponent(ConnectProperties connectProperties) {
        this(connectProperties, new CommonDtuMessageAware());
    }

    public CommonDtuServerComponent(ConnectProperties connectProperties, DtuMessageAware<CommonDtuServerMessage> dtuMessageAware) {
        super(connectProperties, dtuMessageAware);
    }

    @Override
    public String getDesc() {
        return "支持DTU+任意设备协议";
    }

    @Override
    public String getName() {
        return "通用DTU服务";
    }

    @Override
    public AbstractProtocol doGetProtocol(CommonDtuServerMessage message) {
        return remove(message.getHead().getMessageId());
    }
}
