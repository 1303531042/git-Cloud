package com.ruoyi.gateway.modbus.server.rtu;

import com.ruoyi.gateway.protocol.ProtocolType;
import com.ruoyi.gateway.message.DefaultMessageHead;
import com.ruoyi.gateway.modbus.server.tcp.ModbusTcpHeader;
import com.ruoyi.gateway.utils.ByteUtil;
import com.ruoyi.gateway.modbus.server.dtu.ModbusRtuForDtuMessage;

/**
 * @see ModbusRtuBody
 * @see ModbusRtuForDtuMessage
 */
public class ModbusRtuHeader extends DefaultMessageHead {

    private byte unitId; // 单元标识 占1byte (从机地址)

    protected ModbusRtuHeader() {
        this(null, null, null);
    }

    protected ModbusRtuHeader(String equipCode, String messageId, ProtocolType type) {
        super(equipCode, messageId, type);
    }

    /**
     * 构建请求头报文头
     * @return
     */
    public static ModbusRtuHeader buildRequestHeader(byte unitId) {
        ModbusRtuHeader rtuHeader = new ModbusRtuHeader();
        rtuHeader.setUnitId(unitId);
        rtuHeader.setMessage(new byte[]{unitId});
        return rtuHeader;
    }

    /**
     *
     * @param equipCode
     * @param type
     * @return
     */
    public static ModbusRtuHeader buildRequestHeader(String equipCode, String messageId, ProtocolType type) {
        return new ModbusRtuHeader(equipCode, messageId, type);
    }

    public static ModbusRtuHeader buildRequestHeader(byte[] message) {
        return buildResponseHeader(message);
    }

    public static ModbusRtuHeader buildResponseHeader(byte[] message) {
        ModbusRtuHeader header = new ModbusRtuHeader();

        header.unitId = ByteUtil.getByte(message, 0);
        header.setMessage(ByteUtil.subBytes(message, 0, 1));
        return header;
    }

    public static ModbusRtuHeader formModbusTcpHeader(ModbusTcpHeader head) {
        ModbusRtuHeader header = new ModbusRtuHeader();
        header.unitId = head.getUnitId();
        header.setMessage(new byte[]{header.unitId});
        return header;
    }

    public byte getUnitId() {
        return unitId;
    }

    public void setUnitId(byte unitId) {
        this.unitId = unitId;
    }

    @Override
    public String toString() {
        return "ModbusRtuHeader{" +
                "unitId=" + unitId +
                '}';
    }

}
