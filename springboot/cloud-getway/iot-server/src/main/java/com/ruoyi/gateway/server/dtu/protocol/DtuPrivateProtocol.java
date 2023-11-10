package com.ruoyi.gateway.server.dtu.protocol;

import com.ruoyi.gateway.protocol.ProtocolType;
import com.ruoyi.gateway.server.ServerMessage;
import com.ruoyi.gateway.server.dtu.DtuCommonProtocolType;
import com.ruoyi.gateway.server.protocol.ClientInitiativeProtocol;

/**
 * Dtu的私有协议
 */
public class DtuPrivateProtocol extends ClientInitiativeProtocol<ServerMessage> {

    public DtuPrivateProtocol(ServerMessage requestMessage) {
        super(requestMessage);
    }

    @Override
    protected ServerMessage doBuildResponseMessage() {
        return null;
    }

    @Override
    protected void doBuildRequestMessage(ServerMessage requestMessage) {
        if(logger.isDebugEnabled()) {
            logger.debug("DTU设备 私有协议 - 设备编号: {} - 协议类型: {} - 报文: {}"
                    , requestMessage.getHead().getEquipCode(), protocolType(), requestMessage);
        }
    }

    @Override
    public ProtocolType protocolType() {
        return DtuCommonProtocolType.DTU;
    }
}
