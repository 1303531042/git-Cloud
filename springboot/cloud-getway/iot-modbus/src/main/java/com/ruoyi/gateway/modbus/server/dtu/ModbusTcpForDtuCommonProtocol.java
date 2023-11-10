package com.ruoyi.gateway.modbus.server.dtu;

import com.ruoyi.gateway.modbus.ModbusCommonProtocol;
import com.ruoyi.gateway.modbus.ModbusProtocolException;
import com.ruoyi.gateway.modbus.ModbusUtil;
import com.ruoyi.gateway.modbus.Payload;
import com.ruoyi.gateway.modbus.consts.ModbusCode;
import com.ruoyi.gateway.modbus.consts.ModbusCoilStatus;
import com.ruoyi.gateway.modbus.server.tcp.ModbusTcpBody;
import com.ruoyi.gateway.modbus.server.tcp.ModbusTcpHeader;
import com.ruoyi.gateway.modbus.server.tcp.ModbusTcpMessageBuilder;
import com.ruoyi.gateway.modbus.simulation.ModbusSimulationInterfaceImpl;
import com.ruoyi.gateway.modbus.simulation.enums.ModbusMethodEnum;
import com.ruoyi.simulation.annotations.ParamExplain;
import com.ruoyi.simulation.annotations.SimulationClass;
import com.ruoyi.simulation.annotations.SimulationMethod;
import com.ruoyi.gateway.protocol.ProtocolType;
import com.ruoyi.gateway.consts.ExecStatus;
import com.ruoyi.gateway.server.dtu.DtuCommonProtocol;
import com.ruoyi.gateway.server.protocol.ServerInitiativeSyncProtocol;
import com.ruoyi.gateway.utils.ByteUtil;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

/**
 * modbus tcp的服务端通用操作协议, 使用Dtu设备连网
 * 注意：Tcp的报文标识字段只有两个字节({@link Short#MIN_VALUE}-{@link Short#MIN_VALUE})<hr>
 *     1. 所以太频繁的操作同一台客户端可能导致数据被覆盖(建议间隔200-500毫秒操作设备)
 *     2. iot已经做了一定的处理 {@link ModbusTcpHeader#buildMessageId()}
 */
@SimulationClass(ModbusSimulationInterfaceImpl.class)
public class ModbusTcpForDtuCommonProtocol extends ServerInitiativeSyncProtocol<ModbusTcpForDtuMessage>
        implements ModbusCommonProtocol, DtuCommonProtocol<ModbusTcpForDtuMessage> {

    /**
     * 寄存器开始地址
     */
    private int start;

    /**
     * @see ModbusCode or Other
     */
    private ProtocolType type;

    /**
     * Modbus协议有值
     */
    private Payload payload;

    protected ModbusTcpForDtuCommonProtocol(ProtocolType type, ModbusTcpForDtuMessage message) {
        this(0, type, message);
    }

    protected ModbusTcpForDtuCommonProtocol(int start, ProtocolType type, ModbusTcpForDtuMessage message) {
        this.type = type;
        this.start = start;
        this.setRequestMessage(message);
    }

    @Override
    protected ModbusTcpForDtuMessage doBuildRequestMessage() throws IOException {
        return this.requestMessage;
    }

    @Override
    protected void doBuildResponseMessage(ModbusTcpForDtuMessage message) {
        if(this.getExecStatus() == ExecStatus.success
                && this.protocolType() instanceof ModbusCode) {
            ModbusTcpBody body = message.getBody();
            // 异常交由开发者自己处理 https://gitee.com/iteaj/iot/issues/I517Y0
            if(body.getErrCode() != null) {
                return;
            }

            this.payload  = ModbusUtil.resolvePayload(body.getContent(), (short) this.start, body.getCode());
        }
    }

    public Payload getPayload() {
        return payload;
    }

    /**
     * 构建自定义报文
     * @param equipCode 要操作的设备的设备编号
     * @param message
     * @param type
     * @return
     */
    public static ModbusTcpForDtuCommonProtocol build(String equipCode, byte[] message, ProtocolType type) {
        ModbusTcpForDtuMessage tcpForDtuMessage = new ModbusTcpForDtuMessage(equipCode);
        tcpForDtuMessage.setMessage(message);
        tcpForDtuMessage.setBody(ModbusTcpBody.empty());
        tcpForDtuMessage.setHead(ModbusTcpHeader.buildRequestHeader(equipCode, null, type));
        return new ModbusTcpForDtuCommonProtocol(type, tcpForDtuMessage);
    }

    /**
     * 构建Modbus读线圈协议
     * @see ModbusCode#Read01
     * @param equipCode 要操作的设备的设备编号
     * @param device 从机的设备地址
     * @param start 从哪个寄存器开始读
     * @param num 读多少个
     * @return
     */
    @SimulationMethod(EnumClass = ModbusMethodEnum.class,code = 0)
    public static ModbusTcpForDtuCommonProtocol buildRead01(@ParamExplain(id = 1,explain = "设备编号",ignoreBeforeInstance = true)String equipCode, @ParamExplain(id = 2, explain = "从机编号")int device
            , @ParamExplain(id = 3, explain = "起始读取地址")int start, @ParamExplain(id = 4, explain = "读取寄存器个数")int num) {
        ModbusTcpForDtuMessage message = ModbusTcpMessageBuilder.buildRead01Message(new ModbusTcpForDtuMessage(equipCode), device, start, num);
        return new ModbusTcpForDtuCommonProtocol(num, ModbusCode.Read01, message);
    }

    /**
     * 构建Modbus读线圈协议
     * @see ModbusCode#Read02
     * @param equipCode 要操作的设备的设备编号
     * @param device 从机的设备地址
     * @param start 从哪个寄存器开始读
     * @param num 读多少个
     * @return
     */
    @SimulationMethod(EnumClass = ModbusMethodEnum.class,code = 1)
    public static ModbusTcpForDtuCommonProtocol buildRead02(@ParamExplain(id = 1,explain = "设备编号",ignoreBeforeInstance = true)String equipCode, @ParamExplain(id = 2, explain = "从机编号")int device
            , @ParamExplain(id = 3, explain = "起始读取地址")int start, @ParamExplain(id = 4, explain = "读取寄存器个数")int num) {
        ModbusTcpForDtuMessage message = ModbusTcpMessageBuilder.buildRead02Message(new ModbusTcpForDtuMessage(equipCode), device, start, num);
        return new ModbusTcpForDtuCommonProtocol(num, ModbusCode.Read02, message);
    }

    /**
     * 构建Modbus读保持寄存器报文
     * @param equipCode 要操作的设备的设备编号
     * @param device 从机的设备地址
     * @param start 从哪个寄存器开始读
     * @param num 读几个寄存器
     * @return
     */
    @SimulationMethod(EnumClass = ModbusMethodEnum.class,code = 2)
    public static ModbusTcpForDtuCommonProtocol buildRead03(@ParamExplain(id = 1,explain = "设备编号",ignoreBeforeInstance = true)String equipCode, @ParamExplain(id = 2, explain = "从机编号")int device
            , @ParamExplain(id = 3, explain = "起始读取地址")int start, @ParamExplain(id = 4, explain = "读取寄存器个数")int num) {
        ModbusTcpForDtuMessage message = ModbusTcpMessageBuilder.buildRead03Message(new ModbusTcpForDtuMessage(equipCode), device, start, num);
        return new ModbusTcpForDtuCommonProtocol(start, ModbusCode.Read03, message);
    }

    /**
     * 构建Modbus读输入寄存器协议
     * @param equipCode 要操作的设备的设备编号
     * @param device 从机的设备地址 (1-255)
     * @param start 从哪个寄存器开始读 (1-65535)
     * @param num 读几个寄存器(1-2000)
     * @return
     */
    @SimulationMethod(EnumClass = ModbusMethodEnum.class,code = 3)
    public static ModbusTcpForDtuCommonProtocol buildRead04(@ParamExplain(id = 1,explain = "设备编号",ignoreBeforeInstance = true)String equipCode, @ParamExplain(id = 2, explain = "从机编号")int device
            , @ParamExplain(id = 3, explain = "起始读取地址")int start, @ParamExplain(id = 4, explain = "读取寄存器个数")int num) {
        ModbusTcpForDtuMessage message = ModbusTcpMessageBuilder.buildRead04Message(new ModbusTcpForDtuMessage(equipCode), device, start, num);
        return new ModbusTcpForDtuCommonProtocol(start, ModbusCode.Read04, message);
    }

    /**
     * 构建Modbus写单个线圈报文
     * @param equipCode 要操作的设备的设备编号
     * @param device 访问的设备
     * @param start 从哪个寄存器开始写
     * @param status 写内容
     * @return
     */
    @SimulationMethod(EnumClass = ModbusMethodEnum.class,code = 4)
    public static ModbusTcpForDtuCommonProtocol buildWrite05(@ParamExplain(id = 1,explain = "设备编号",ignoreBeforeInstance = true)String equipCode, @ParamExplain(id = 2, explain = "从机编号")int device
            , @ParamExplain(id = 3, explain = "起始写入地址")int start, @ParamExplain(id = 4, explain = "写入内容")ModbusCoilStatus status) {
        ModbusTcpForDtuMessage message = ModbusTcpMessageBuilder.buildWrite05Message(new ModbusTcpForDtuMessage(equipCode), device, start, status);
        return new ModbusTcpForDtuCommonProtocol(ModbusCode.Write05, message);
    }

    /**
     * 构建Modbus写单个寄存器报文
     * @param equipCode 要操作的设备的设备编号
     * @param device 从机的设备地址 (1-255)
     * @param start 从哪个寄存器开始写 (1-65535)
     * @param write 写内容
     * @return
     */
    public static ModbusTcpForDtuCommonProtocol buildWrite06(@ParamExplain(id = 1,explain = "设备编号",ignoreBeforeInstance = true)String equipCode, @ParamExplain(id = 2, explain = "从机编号")int device
            , @ParamExplain(id = 3, explain = "起始写入地址")int start, @ParamExplain(id = 4, explain = "写入内容")byte[] write) {
        ModbusTcpForDtuMessage message = ModbusTcpMessageBuilder.buildWrite06Message(new ModbusTcpForDtuMessage(equipCode), device, start, write);
        return new ModbusTcpForDtuCommonProtocol(ModbusCode.Write06, message);
    }

    /**
     * 构建Modbus写单个寄存器报文
     * @param equipCode 要操作的设备的设备编号
     * @param device 从机的设备地址 (1-255)
     * @param start 从哪个寄存器开始写 (1-65535)
     * @return
     */
    @SimulationMethod(EnumClass = ModbusMethodEnum.class,code = 5)
    public static ModbusTcpForDtuCommonProtocol buildWrite06(@ParamExplain(id = 1,explain = "设备编号",ignoreBeforeInstance = true)String equipCode, @ParamExplain(id = 2, explain = "从机编号")int device
            , @ParamExplain(id = 3, explain = "起始写入地址")int start, @ParamExplain(id = 4, explain = "写入内容")short value) {
        byte[] write = ByteUtil.getBytesOfReverse(value);
        ModbusTcpForDtuMessage message = ModbusTcpMessageBuilder.buildWrite06Message(new ModbusTcpForDtuMessage(equipCode), device, start, write);
        return new ModbusTcpForDtuCommonProtocol(ModbusCode.Write06, message);
    }

    /**
     * 构建Modbus写多个寄存器报文
     * @param equipCode 要操作的设备的设备编号
     * @param device 从机的设备地址 (1-255)
     * @param start 从哪个寄存器开始写
     * @param write 写到设备的内容 1Byte = 8bit = 8个线圈
     * @return
     */
    @SimulationMethod(EnumClass = ModbusMethodEnum.class,code = 6)
    public static ModbusTcpForDtuCommonProtocol buildWrite0F(@ParamExplain(id = 1,explain = "设备编号",ignoreBeforeInstance = true)String equipCode, @ParamExplain(id = 2, explain = "从机编号")int device
            , @ParamExplain(id = 3, explain = "起始写入地址")int start, @ParamExplain(id = 4, explain = "写入内容")byte[] write) {
        ModbusTcpForDtuMessage message = ModbusTcpMessageBuilder.buildWrite0FMessage(new ModbusTcpForDtuMessage(equipCode), device, start, write);
        return new ModbusTcpForDtuCommonProtocol(ModbusCode.Write0F, message);
    }

    /**
     * 构建Modbus写多个寄存器报文
     * @param equipCode 要操作的设备的设备编号
     * @param device 从机的设备地址 (1-255)
     * @param start 从哪个寄存器开始写
     * @param num 写几个寄存器
     * @param write 写到设备的内容
     * @return
     */
    @SimulationMethod(EnumClass = ModbusMethodEnum.class,code = 7)
    public static ModbusTcpForDtuCommonProtocol buildWrite10(@ParamExplain(id = 1,explain = "设备编号",ignoreBeforeInstance = true)String equipCode, @ParamExplain(id = 2, explain = "从机编号")int device
            ,  @ParamExplain(id = 3, explain = "起始写入地址")int start, @ParamExplain(id = 4, explain = "写入寄存器个数")int num, @ParamExplain(id = 5, explain = "写入内容")byte[] write) {
        ModbusTcpForDtuMessage message = ModbusTcpMessageBuilder.buildWrite10Message(new ModbusTcpForDtuMessage(equipCode), device, start, num, write);
        return new ModbusTcpForDtuCommonProtocol(ModbusCode.Write10, message);
    }

    /**
     * 构建Modbus写多个寄存器报文(字符串类型使用UTF-8)
     * @see ByteUtil#getBytes(int)
     * @see ByteUtil#getBytes(byte)
     * @see ByteUtil#getBytes(long)
     * @see ByteUtil#getBytes(float)
     * ......
     * @param device 从机的设备地址 (1-255)
     * @param start 从哪个寄存器开始写
     * @param equipCode 要操作的设备的设备编号
     * @param args 写到设备的内容(可以是Number类型和String类型) 如果是字符串类型使用UTF-8编码
     * @return
     */
    public static ModbusTcpForDtuCommonProtocol buildWrite10(String equipCode, int device, int start, Object... args) {
        if(ObjectUtils.isEmpty(args)) {
            throw new ModbusProtocolException("未指定要写的内容", ModbusCode.Write10);
        }

        ModbusUtil.Write10Build write10Build = ModbusUtil.write10Build(args);
        ModbusTcpForDtuMessage message = ModbusTcpMessageBuilder.buildWrite10Message(
                new ModbusTcpForDtuMessage(equipCode), device, start, write10Build.num, write10Build.message);
        return new ModbusTcpForDtuCommonProtocol(ModbusCode.Write10, message);
    }

    public int getStart() {
        return start;
    }

    @Override
    public ProtocolType protocolType() {
        return this.type;
    }
}
