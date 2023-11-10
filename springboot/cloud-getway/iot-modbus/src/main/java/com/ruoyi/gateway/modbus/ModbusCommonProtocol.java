package com.ruoyi.gateway.modbus;

import com.ruoyi.gateway.modbus.simulation.ModbusRtuSimulationInterfaceImpl;
import com.ruoyi.gateway.protocol.Protocol;
import com.ruoyi.gateway.protocol.ProtocolType;
import com.ruoyi.gateway.modbus.consts.ModbusCode;
import com.ruoyi.simulation.annotations.SimulationClass;

/**
 * 居于iot框架实现的通用的Modbus操作协议
 */
public interface ModbusCommonProtocol extends Protocol {

    Payload getPayload();

    /**
     * @see ModbusCode or other
     * @return
     */
    ProtocolType protocolType();
}
