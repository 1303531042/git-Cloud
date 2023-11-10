package com.ruoyi.gateway.modbus.server.dtu;

import com.ruoyi.gateway.protocol.AbstractProtocol;
import com.ruoyi.gateway.protocol.Protocol;
import com.ruoyi.gateway.config.ConnectProperties;
import com.ruoyi.gateway.modbus.server.tcp.ModbusTcpHeader;
import com.ruoyi.gateway.modbus.server.tcp.ModbusTcpMessageBuilder;
import com.ruoyi.gateway.server.dtu.DtuDecoderServerComponent;
import com.ruoyi.gateway.server.dtu.DtuMessageType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.Optional;

/**
 * 适用于：<hr>
 *     首先使用Dtu连网, dtu必须第一个报文必须上报设备编号
 *     其次dtu连接的设备必须是使用标准的Modbus Tcp协议
 * @param <M>
 */
public class ModbusTcpForDtuServerComponent<M extends ModbusTcpForDtuMessage> extends DtuDecoderServerComponent<M> {

    public ModbusTcpForDtuServerComponent(ConnectProperties connectProperties) {
        this(connectProperties, new ModbusTcpMessageAware<>());
    }

    public ModbusTcpForDtuServerComponent(ConnectProperties connectProperties, DtuMessageType messageType) {
        this(connectProperties, new ModbusTcpMessageAware<>(messageType));
    }

    public ModbusTcpForDtuServerComponent(ConnectProperties connectProperties, ModbusTcpMessageAware<M> dtuMessageAware) {
        super(connectProperties, dtuMessageAware);
    }

    @Override
    public String getName() {
        return "ModbusTcpForDtu";
    }

    @Override
    public String getDesc() {
        return "使用Dtu连网且设备基于标准Modbus Tcp协议的iot服务端实现";
    }

    @Override
    public AbstractProtocol doGetProtocol(M message) {
        return remove(message.getHead().getMessageId());
    }

    @Override
    public Optional<ChannelFuture> writeAndFlush(String equipCode, Protocol protocol) {
        Channel channel = getDeviceManager().find(equipCode);
        if(channel != null) {
            ModbusTcpHeader head = (ModbusTcpHeader)protocol.requestMessage().getHead();

            // 设置Modbus的递增值和messageId
            short nextId = ModbusTcpMessageBuilder.getNextId(channel);
            ModbusTcpMessageBuilder.buildMessageHeadByNextId(nextId, head);
        }

        return super.writeAndFlush(equipCode, protocol);
    }

    @Override
    public M createMessage(byte[] message) {
        return (M) new ModbusTcpForDtuMessage(message);
    }
}
