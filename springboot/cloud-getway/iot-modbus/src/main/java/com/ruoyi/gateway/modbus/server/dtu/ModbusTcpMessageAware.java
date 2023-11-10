package com.ruoyi.gateway.modbus.server.dtu;

import com.ruoyi.gateway.modbus.consts.ModbusCode;
import com.ruoyi.gateway.modbus.consts.ModbusErrCode;
import com.ruoyi.gateway.server.dtu.DefaultDtuMessageAware;
import com.ruoyi.gateway.server.dtu.DtuCommonProtocolType;
import com.ruoyi.gateway.server.dtu.DtuMessageType;
import com.ruoyi.gateway.utils.ByteUtil;
import io.netty.buffer.ByteBuf;

public class ModbusTcpMessageAware<M extends ModbusTcpForDtuMessage> extends DefaultDtuMessageAware<M> {

    public ModbusTcpMessageAware() { }

    public ModbusTcpMessageAware(DtuMessageType messageType) {
        super(messageType);
    }

    @Override
    protected M customizeType(String equipCode, byte[] message, ByteBuf msg) {
        // modbus tcp 协议报文长度不能小于8
        // 小于8说明是dtu的报文
        if(message.length < 8) {
            return null; // 不能校验 等待下一个报文
        }

        // 获取modbus tcp的功能码
        int code = message[7] & 0xFF;
        if(code > 0x80) { // 读取到的功能码可能是modbus的异常码, 异常码大于 0x80
            code = code - 0x80;
            try {
                ModbusCode.INSTANCE((byte) code);
                ModbusErrCode.valueOf(message[8]);
            } catch (IllegalStateException e) {
                return createMessage(DtuCommonProtocolType.DTU, msg);
            }
        }

        // modbus的功能码只能是在 0x01 - 0x10之间
        if(code >= 0x01 && code <= 0x10) {
            try {
                ModbusCode.INSTANCE((byte) code);
            } catch (IllegalStateException e) { // 不是modbus协议的功能码
                return createMessage(DtuCommonProtocolType.DTU, msg);
            }
        } else { // 不存在功能码说明不是modbus协议
            return createMessage(DtuCommonProtocolType.DTU, msg);
        }

        short length = ByteUtil.bytesToShortOfReverse(message, 4);
        if(message.length < length + 6) {
            return null; // 等待下一个报文
        }

        // 交由下个modbus协议处理器处理
        return createMessage(length + 6, msg);
    }

}
