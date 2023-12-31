package com.ruoyi.gateway.proxy;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.gateway.server.ServerMessage;
import com.ruoyi.gateway.utils.ByteUtil;

/**
 * create time: 2021/8/9
 *
 * @author iteaj
 * @since 1.0
 */
public class ProxyServerMessage extends ServerMessage {

    public ProxyServerMessage(byte[] message) {
        super(message);
    }

    public ProxyServerMessage(ProxyClientMessageHead head) {
        super(head);
    }

    public ProxyServerMessage(ProxyClientMessageHead head, ProxyClientMessageBody body) {
        super(head, body);
    }

    @Override
    protected ProxyClientMessageHead doBuild(byte[] message) {
        int headLength = ByteUtil.bytesToInt(message, 0);

        byte[] bodyMsg = ByteUtil.subBytes(message, headLength + 4);
        this.messageBody = JSONObject.parseObject(bodyMsg, ProxyClientJsonMessageBody.class);

        return ProxyClientMessageUtil.decoder(message);
    }

    public String getDeviceSn() {
        return getHead().getEquipCode();
    }

    @Override
    public ProxyClientMessageHead getHead() {
        return (ProxyClientMessageHead) super.getHead();
    }

    @Override
    public ProxyClientMessageBody getBody() {
        return (ProxyClientMessageBody) super.getBody();
    }
}
