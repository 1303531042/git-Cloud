package com.ruoyi.gateway.udp;

import com.ruoyi.gateway.message.Message;
import com.ruoyi.gateway.protocol.ProtocolType;
import com.ruoyi.gateway.message.DefaultMessageHead;

public class UdpMessageHead extends DefaultMessageHead {

    public UdpMessageHead() {
        super(Message.EMPTY);
    }

    public UdpMessageHead(byte[] message) {
        super(message);
    }

    public UdpMessageHead(ProtocolType type) {
        super(null, null, type);
    }

    public UdpMessageHead(String equipCode, String messageId, ProtocolType type) {
        super(equipCode, messageId, type);
    }

}
