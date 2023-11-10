package com.ruoyi.gateway.modbus.server.dtu;

import com.ruoyi.gateway.protocol.ProtocolType;
import com.ruoyi.gateway.modbus.server.rtu.ModbusRtuBody;
import com.ruoyi.gateway.modbus.server.rtu.ModbusRtuHeader;
import com.ruoyi.gateway.modbus.server.rtu.ModbusRtuServerMessage;
import com.ruoyi.gateway.server.dtu.message.DtuMessage;

public class ModbusRtuForDtuMessage extends ModbusRtuServerMessage implements DtuMessage {

    private ProtocolType protocolType;

    public ModbusRtuForDtuMessage(String equipCode) {
        super(equipCode);
    }

    public ModbusRtuForDtuMessage(byte[] message) {
        super(message);
    }

    public ModbusRtuForDtuMessage(ModbusRtuHeader head) {
        super(head);
    }

    public ModbusRtuForDtuMessage(ModbusRtuHeader head, ModbusRtuBody body) {
        super(head, body);
    }

    @Override
    protected ModbusRtuHeader doBuild(byte[] message) {
        if(getProtocolType() != null) {
            return ModbusRtuHeader.buildRequestHeader(this.getEquipCode(), getChannelId(), getProtocolType());
        }

        return super.doBuild(message);
    }

    @Override
    public ProtocolType getProtocolType() {
        return protocolType;
    }

    @Override
    public ModbusRtuForDtuMessage setProtocolType(ProtocolType protocolType) {
        this.protocolType = protocolType;
        return this;
    }
}
