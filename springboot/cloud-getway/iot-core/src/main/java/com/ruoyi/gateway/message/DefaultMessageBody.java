package com.ruoyi.gateway.message;

import static com.ruoyi.gateway.message.Message.EMPTY;

public class DefaultMessageBody implements Message.MessageBody {

    private byte[] message;

    /**
     * 使用空报文
     */
    public DefaultMessageBody() {
        this(EMPTY);
    }

    public DefaultMessageBody(byte[] message) {
        this.message = message;
    }

    @Override
    public byte[] getMessage() {
        return this.message;
    }
}
