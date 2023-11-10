package com.ruoyi.gateway.server.dtu.message;

import com.ruoyi.gateway.message.Message;
import com.ruoyi.gateway.protocol.ProtocolType;

public interface DtuMessage extends Message {

    String getEquipCode();

    void setEquipCode(String equipCode);

    ProtocolType getProtocolType();

    DtuMessage setProtocolType(ProtocolType type);

}
