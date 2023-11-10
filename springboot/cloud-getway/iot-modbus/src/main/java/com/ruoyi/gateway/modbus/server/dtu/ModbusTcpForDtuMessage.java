package com.ruoyi.gateway.modbus.server.dtu;

import com.ruoyi.gateway.protocol.ProtocolType;
import com.ruoyi.gateway.modbus.server.tcp.ModbusTcpBody;
import com.ruoyi.gateway.modbus.server.tcp.ModbusTcpHeader;
import com.ruoyi.gateway.modbus.server.tcp.ModbusTcpServerMessage;
import com.ruoyi.gateway.server.dtu.message.DtuMessage;

public class ModbusTcpForDtuMessage extends ModbusTcpServerMessage implements DtuMessage {

    private ProtocolType protocolType;

    public ModbusTcpForDtuMessage(byte[] message) {
        super(message);
    }

    public ModbusTcpForDtuMessage(String equipCode) {
        super(equipCode);
    }

    public ModbusTcpForDtuMessage(ModbusTcpHeader head) {
        super(head);
    }

    public ModbusTcpForDtuMessage(ModbusTcpHeader head, ModbusTcpBody body) {
        super(head, body);
    }

    @Override
    protected ModbusTcpHeader doBuild(byte[] message) {
        if(getProtocolType() != null) {
            return ModbusTcpHeader.buildRequestHeader(this.getEquipCode(), this.getEquipCode(), getProtocolType());
        }

        return super.doBuild(message);
    }

    @Override
    public ProtocolType getProtocolType() {
        return protocolType;
    }

    @Override
    public ModbusTcpForDtuMessage setProtocolType(ProtocolType protocolType) {
        this.protocolType = protocolType;
        return this;
    }
}
