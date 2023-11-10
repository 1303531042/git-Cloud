package com.ruoyi.gateway.server.protocol;

import com.ruoyi.gateway.message.Message;
import com.ruoyi.gateway.protocol.ProtocolType;
import com.ruoyi.gateway.protocol.CommonProtocolType;
import com.ruoyi.gateway.server.component.SocketServerComponent;
import com.ruoyi.gateway.server.ServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create time: 2021/3/6
 *  通用的心跳协议
 *  此协议不响应客户端, 只做日志记录
 * @author iteaj
 * @since 1.0
 */
public class HeartbeatProtocol extends ClientInitiativeProtocol<ServerMessage> {

    private SocketServerComponent serverComponent;
    private static Logger logger = LoggerFactory.getLogger(HeartbeatProtocol.class);

    protected HeartbeatProtocol(ServerMessage requestMessage) {
        super(requestMessage);
    }

    public static HeartbeatProtocol getInstance(ServerMessage requestMessage) {
        return new HeartbeatProtocol(requestMessage);
    }

    @Override
    public ProtocolType protocolType() {
        return CommonProtocolType.Common_Heart;
    }

    @Override
    protected ServerMessage doBuildResponseMessage() {
        return null;
    }

    public SocketServerComponent getServerComponent() {
        return serverComponent;
    }

    public HeartbeatProtocol setServerComponent(SocketServerComponent serverComponent) {
        this.serverComponent = serverComponent;
        return this;
    }

    @Override
    protected void doBuildRequestMessage(ServerMessage requestMessage) {
        if(logger.isTraceEnabled()) {
            Message.MessageHead head = requestMessage.getHead();
            logger.trace("客户端心跳({}) 客户端编号：{} - messageId：{} - 协议：{}", serverComponent.getName()
                    , head.getEquipCode(), head.getMessageId(), head.getType());
        }
    }
}
