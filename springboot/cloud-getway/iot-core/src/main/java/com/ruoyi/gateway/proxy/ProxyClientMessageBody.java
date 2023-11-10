package com.ruoyi.gateway.proxy;

import com.ruoyi.gateway.message.Message;

public interface ProxyClientMessageBody extends Message.MessageBody {

    static ProxyClientJsonMessageBody jsonMessageBody() {
        return new ProxyClientJsonMessageBody();
    }

    static ProxyClientJsonMessageBody jsonMessageBody(String key, Object value) {
        return new ProxyClientJsonMessageBody().add(key, value);
    }

    /**
     * 代理设备编号
     * @return
     */
    String getDeviceSn();

    /**
     * ctrl字符串 {@code com.iteaj.network.client.handle.IotCtrl#value()}
     * @return
     */
    String getCtrl();

    ProxyClientMessageBody setMessage(byte[] message);
}
