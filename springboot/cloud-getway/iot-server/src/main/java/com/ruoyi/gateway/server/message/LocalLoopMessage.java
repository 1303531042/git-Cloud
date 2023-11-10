package com.ruoyi.gateway.server.message;

import com.ruoyi.gateway.message.Message;
import com.ruoyi.gateway.message.DefaultMessageHead;
import com.ruoyi.gateway.protocol.CommonProtocolType;
import com.ruoyi.gateway.server.ServerMessage;
import com.ruoyi.gateway.server.protocol.LocalLoopProtocol;

import java.util.UUID;

/**
 * <p>本地回环报文</p>
 * @see LocalLoopProtocol  使用此报文的协议基类
 * Create Date By 2017-09-29
 * @author iteaj
 * @since 1.7
 */
public class LocalLoopMessage extends ServerMessage {

    private Object param; //本地回环参数
    private String deviceSn;

    public LocalLoopMessage(byte[] message) {
        super(message);
    }

    public LocalLoopMessage(LocalLoopHead head) {
        super(head);
    }

    public LocalLoopMessage(LocalLoopHead head, LocalLoopBody body) {
        super(head, body);
    }

    @Override
    protected DefaultMessageHead doBuild(byte[] message) {
        return null;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public static class LocalLoopHead implements Message.MessageHead {
        private String equipCode;
        private String messageId;

        public LocalLoopHead(String equipCode) {
            this(equipCode, UUID.randomUUID().toString());
        }

        public LocalLoopHead(String equipCode, String messageId) {
            this.equipCode = equipCode;
            this.messageId = messageId;
        }

        @Override
        public String getEquipCode() {
            return this.equipCode;
        }

        @Override
        public LocalLoopHead setEquipCode(String equipCode) {
            this.equipCode = equipCode; return this;
        }

        @Override
        public String getMessageId() {
            return this.messageId;
        }

        @Override
        public LocalLoopHead setMessageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        @Override
        public Object getType() {
            return CommonProtocolType.LocalLoop;
        }

        @Override
        public byte[] getMessage() {
            return Message.EMPTY;
        }
    }

    public class LocalLoopBody implements Message.MessageBody {

        @Override
        public byte[] getMessage() {
            return Message.EMPTY;
        }
    }
}
