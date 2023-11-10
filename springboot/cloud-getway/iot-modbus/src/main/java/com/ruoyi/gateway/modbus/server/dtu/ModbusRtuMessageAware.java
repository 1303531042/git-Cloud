package com.ruoyi.gateway.modbus.server.dtu;

import com.ruoyi.gateway.modbus.consts.ModbusCode;
import com.ruoyi.gateway.modbus.consts.ModbusErrCode;
import com.ruoyi.gateway.server.dtu.DefaultDtuMessageAware;
import com.ruoyi.gateway.server.dtu.DtuCommonProtocolType;
import com.ruoyi.gateway.server.dtu.DtuMessageType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ModbusRtuMessageAware<M extends ModbusRtuForDtuMessage> extends DefaultDtuMessageAware<M> {

    public ModbusRtuMessageAware() { }

    public ModbusRtuMessageAware(DtuMessageType messageType) {
        super(messageType);
    }

    /**
     * 校验是否是标准的modbus协议
     * @see ModbusRtuForDtuServerComponent#doTcpDecode(ChannelHandlerContext, ByteBuf)
     * @param equipCode
     * @param message
     * @param msg
     * @return 如果是标准的modbus rtu协议返回 {@code null} 否则返回dtu私有协议 {@link DtuCommonProtocolType#DTU}
     */
    @Override
    protected M customizeType(String equipCode, byte[] message, ByteBuf msg) {
        // modbus rtu 协议报文长度不能小于2
        // 小于5说明是dtu的报文
        if(message.length < 3) {
            return null; // 不能判断, 等待下一个报文
        }

        // 获取modbus tcp的功能码
        int code = message[1] & 0xFF;
        if(code > 0x80) { // 读取到的功能码可能是modbus的异常码, 异常码大于 0x80
            code = code - 0x80;
            try {
                ModbusCode.INSTANCE((byte) code);
                ModbusErrCode.valueOf(message[2]);

                if(message.length < 5) {
                    return null; // 等待下一个报文
                }

                // 读取5个字节的modbus异常报文
                return createMessage(5, msg);
            } catch (IllegalStateException e) {
                return createMessage(DtuCommonProtocolType.DTU, msg);
            }
        }

        // modbus的功能码只能是在 0x01 - 0x10之间
        if(code >= 0x01 && code <= 0x10) {
            try {
                ModbusCode.INSTANCE((byte) code);
                // todo 以下的长度判断：如果对于一个标准的modbus报文拆分成两包则会出现校验成DTU包的问题
                // 所以在读取报文时读取的寄存器数量尽量少 不要太多导致一个报文拆成两个
                if(code <= 0x04) { // 读报文校验
                    int length = message[2] & 0xFF;
                    if(message.length < length + 5) {
                        return null;
                    } else {
                        // 读取rtu报文
                        return createMessage(length + 5, msg);
                    }
                } else { // 写报文校验
                    if(message.length < 8) {
                        return null;
                    } else {
                        // 读取rtu报文
                        return createMessage(8, msg);
                    }
                }
            } catch (IllegalStateException e) { // 不是modbus协议的功能码
                return createMessage(DtuCommonProtocolType.DTU, msg);
            }
        } else { // 不存在功能码说明不是modbus协议
            return createMessage(DtuCommonProtocolType.DTU, msg);
        }
    }

}
