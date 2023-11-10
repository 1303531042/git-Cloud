package com.ruoyi.gateway.server.dtu.message;

import com.ruoyi.gateway.protocol.ProtocolType;
import com.ruoyi.gateway.message.DefaultMessageHead;
import com.ruoyi.gateway.server.ServerMessage;

public abstract class DtuServerMessageAbstract extends ServerMessage implements DtuMessage {

    private String equipCode;
    private ProtocolType protocolType;

    public DtuServerMessageAbstract(byte[] message) {
        super(message);
    }

    public DtuServerMessageAbstract(MessageHead head) {
        super(head);
    }

    public DtuServerMessageAbstract(MessageHead head, MessageBody body) {
        super(head, body);
    }

    @Override
    protected MessageHead doBuild(byte[] message) {
        if(getProtocolType() != null) {
            return new DefaultMessageHead(getEquipCode(), getEquipCode(), getProtocolType());
        } else {
            MessageHead messageHead = doBuild(message, getEquipCode());
            if(messageHead != null) {
                this.protocolType = messageHead.getType();
            }

            return messageHead;
        }
    }

    protected abstract MessageHead doBuild(byte[] message, String equipCode);

    @Override
    public String getEquipCode() {
        return this.equipCode;
    }

    @Override
    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode;
    }

    @Override
    public ProtocolType getProtocolType() {
        return this.protocolType;
    }

    @Override
    public DtuMessage setProtocolType(ProtocolType type) {
        this.protocolType = type;
        return this;
    }
}
