package com.ruoyi.gateway.modbus.server.dtu;

import com.ruoyi.gateway.protocol.AbstractProtocol;
import com.ruoyi.gateway.config.ConnectProperties;
import com.ruoyi.gateway.server.dtu.DtuMessageType;
import com.ruoyi.gateway.server.dtu.DtuDecoderServerComponent;

/**
 * 适用于：对于Dtu连网的且设备使用Modbus Rtu协议进行数据交互的情况
 * 注意：使用此解码组件需要需用使用同步的方式操作设备(由于不处理粘包的情况)
 */
public class ModbusRtuForDtuServerComponent<M extends ModbusRtuForDtuMessage> extends DtuDecoderServerComponent<M> {

    public ModbusRtuForDtuServerComponent(ConnectProperties connectProperties) {
        this(connectProperties, new ModbusRtuMessageAware());
    }

    public ModbusRtuForDtuServerComponent(ConnectProperties connectProperties, DtuMessageType messageType) {
        this(connectProperties, new ModbusRtuMessageAware(messageType));
    }

    public ModbusRtuForDtuServerComponent(ConnectProperties connectProperties, ModbusRtuMessageAware<M> dtuMessageAwareDelegation) {
        super(connectProperties, dtuMessageAwareDelegation);
    }

    @Override
    public String getName() {
        return "ModbusRtuForDtu";
    }

    @Override
    public String getDesc() {
        return "使用Dtu连网且设备基于标准Modbus Rtu协议的iot服务端实现";
    }

    @Override
    public AbstractProtocol doGetProtocol(ModbusRtuForDtuMessage message) {
        return remove(message.getHead().getMessageId());
    }

    @Override
    public M createMessage(byte[] message) {
        return (M) new ModbusRtuForDtuMessage(message);
    }

}
