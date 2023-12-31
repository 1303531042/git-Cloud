package com.ruoyi.gateway.modbus.client.rtu;

import com.ruoyi.gateway.client.ClientMessage;
import com.ruoyi.gateway.modbus.server.rtu.ModbusRtuBody;
import com.ruoyi.gateway.modbus.server.rtu.ModbusRtuHeader;
import com.ruoyi.gateway.modbus.server.rtu.ModbusRtuMessage;
import com.ruoyi.gateway.utils.ByteUtil;
import com.ruoyi.gateway.modbus.ModbusUtil;

public class ModbusRtuClientMessage extends ClientMessage implements ModbusRtuMessage {

    private String equipCode;

    public ModbusRtuClientMessage(byte[] message) {
        super(message);
    }

    public ModbusRtuClientMessage(ModbusRtuHeader head) {
        super(head);
    }

    public ModbusRtuClientMessage(ModbusRtuHeader head, ModbusRtuBody body) {
        super(head, body);
    }

    /**
     * 进行CRC计算
     */
    @Override
    public void writeBuild() {
        super.writeBuild();
        String crc = ModbusUtil.getCRC(ByteUtil.subBytes(this.message
                , 0, this.message.length - 2), true);

        byte[] bytes = ByteUtil.hexToByte(crc);
        this.message[this.length() - 1] = bytes[1];
        this.message[this.length() - 2] = bytes[0];
    }

    @Override
    protected ModbusRtuHeader doBuild(byte[] message) {
        this.messageBody = ModbusRtuBody.buildRequestBody(message);
        ModbusRtuHeader rtuHeader = ModbusRtuHeader.buildRequestHeader(message);
        rtuHeader.setType(getBody().getCode());
        rtuHeader.setEquipCode(this.getEquipCode());
        return rtuHeader;
    }

    @Override
    public ModbusRtuHeader getHead() {
        return (ModbusRtuHeader) super.getHead();
    }

    @Override
    public ModbusRtuBody getBody() {
        return (ModbusRtuBody) super.getBody();
    }

    @Override
    public String getEquipCode() {
        return equipCode;
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode;
    }
}
