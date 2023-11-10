package com.ruoyi.gateway.server.dtu.protocol;

import com.ruoyi.gateway.message.Message;
import com.ruoyi.gateway.protocol.ProtocolType;
import com.ruoyi.gateway.server.ServerMessage;
import com.ruoyi.gateway.server.dtu.DtuCommonProtocolType;
import com.ruoyi.gateway.server.dtu.DtuProtocolException;
import com.ruoyi.gateway.server.dtu.message.DtuMessage;
import com.ruoyi.gateway.server.protocol.HeartbeatProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DtuHeartbeatProtocol extends HeartbeatProtocol {

    private static Logger logger = LoggerFactory.getLogger(DtuHeartbeatProtocol.class);

    public DtuHeartbeatProtocol(ServerMessage requestMessage) {
        super(requestMessage);
        if(!(requestMessage instanceof DtuMessage)) {
            throw new DtuProtocolException("不支持的报文类型 期待[DtuMessage]", protocolType());
        }
    }

    @Override
    protected ServerMessage doBuildResponseMessage() {
        return null;
    }

    @Override
    protected void doBuildRequestMessage(ServerMessage requestMessage) {
        if(logger.isDebugEnabled()) {
            Message.MessageHead head = requestMessage.getHead();
            logger.debug("Dtu设备 心跳包 设备编号：{} - 协议类型：{} - 报文: {}"
                    , head.getEquipCode(), protocolType(), requestMessage);
        }
    }

    @Override
    public ProtocolType protocolType() {
        return DtuCommonProtocolType.HEARTBEAT;
    }
}
