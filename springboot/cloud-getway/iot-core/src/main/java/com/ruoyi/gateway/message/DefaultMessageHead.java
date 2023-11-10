package com.ruoyi.gateway.message;

import com.ruoyi.gateway.ProtocolFactory;
import com.ruoyi.gateway.ProtocolPreservable;
import com.ruoyi.gateway.protocol.Protocol;
import com.ruoyi.gateway.protocol.ProtocolType;

/**
 * 默认报文头
 * @see Message
 */
public class DefaultMessageHead implements Message.MessageHead {

    private byte[] message;

    /**
     * 设备编号
     */
    private String equipCode;

    /**
     * 报文id
     * @see ProtocolPreservable#relationKey()
     */
    private String messageId;

    /**
     * 协议类型, 用来区分各自的协议
     * @see Protocol#protocolType()
     * @see ProtocolFactory#getProtocol(SocketMessage) 通过此类型获取不同的协议
     */
    private ProtocolType type;

    public DefaultMessageHead(byte[] message) {
        this.message = message;
    }

    public DefaultMessageHead(String equipCode, String messageId, ProtocolType type) {
        this(Message.EMPTY);
        this.type = type;
        this.equipCode = equipCode;
        this.messageId = messageId;
    }

    public <T extends DefaultMessageHead> T build(byte[] message) {
        this.message = message;
        return (T) this;
    }

    @Override
    public String getEquipCode() {
        return this.equipCode;
    }

    @Override
    public String getMessageId() {
        return this.messageId;
    }

    @Override
    public ProtocolType getType() {
        return this.type;
    }

    @Override
    public byte[] getMessage() {
        return this.message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public void setType(ProtocolType type) {
        this.type = type;
    }

    public DefaultMessageHead setEquipCode(String equipCode) {
        this.equipCode = equipCode; return this;
    }

    public DefaultMessageHead setMessageId(String messageId) {
        this.messageId = messageId; return this;
    }
}
