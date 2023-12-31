package com.ruoyi.gateway.modbus;

import com.ruoyi.gateway.message.Message;

/**
 * 写负载
 * @see Message#EMPTY 无负载数据
 */
public class WritePayload extends Payload{

    private static WritePayload payload = new WritePayload();

    protected WritePayload() {
        super(Message.EMPTY);
    }

    public static WritePayload getInstance() {
        return payload;
    }
}
