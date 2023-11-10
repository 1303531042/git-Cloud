package com.ruoyi.gateway.server.dtu.protocol;

import com.ruoyi.gateway.protocol.ProtocolType;
import com.ruoyi.gateway.server.ServerMessage;
import com.ruoyi.gateway.server.dtu.DtuCommonProtocolType;
import com.ruoyi.gateway.server.protocol.ClientInitiativeProtocol;

public class DtuDeviceSnProtocol extends ClientInitiativeProtocol<ServerMessage> {

    public DtuDeviceSnProtocol(ServerMessage requestMessage) {
        super(requestMessage);
    }

    @Override
    protected ServerMessage doBuildResponseMessage() {
        return null;
    }

    @Override
    protected void doBuildRequestMessage(ServerMessage requestMessage) {
        if(logger.isDebugEnabled()) {
            logger.debug("DTU设备 注册包 - 设备编号: {} - 协议类型: {} - 报文: {}"
                    , requestMessage.getHead().getEquipCode(), protocolType(), requestMessage);
        }
    }

    @Override
    public ProtocolType protocolType() {
        return DtuCommonProtocolType.DEVICE_SN;
    }
}
