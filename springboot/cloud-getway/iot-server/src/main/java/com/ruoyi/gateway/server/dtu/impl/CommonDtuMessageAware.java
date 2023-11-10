package com.ruoyi.gateway.server.dtu.impl;

import com.ruoyi.gateway.server.dtu.DefaultDtuMessageAware;
import com.ruoyi.gateway.server.dtu.DtuMessageType;
import io.netty.buffer.ByteBuf;

public class CommonDtuMessageAware extends DefaultDtuMessageAware<CommonDtuServerMessage> {

    public CommonDtuMessageAware() { }

    public CommonDtuMessageAware(DtuMessageType messageType) {
        super(messageType);
    }

    @Override
    protected byte[] readDtuMsg(String equipCode, byte[] message, ByteBuf msg) {
        CommonDtuServerComponent component = (CommonDtuServerComponent) this.getDecoder();
        // 平台没有主动请求 说明是dtu私有协议
        if(!component.isExists(equipCode)) {
            byte[] msgBytes = new byte[msg.readableBytes()];
            msg.readBytes(msgBytes);
            return msgBytes;
        }

        return super.readDtuMsg(equipCode, message, msg);
    }
}
