package com.ruoyi.gateway.modbus.simulation;

import com.ruoyi.gateway.modbus.server.dtu.ModbusTcpForDtuMessage;
import com.ruoyi.gateway.modbus.simulation.enums.ModbusMethodEnum;
import com.ruoyi.gateway.modbus.server.dtu.ModbusTcpForDtuCommonProtocol;
import com.ruoyi.gateway.server.AbstractGateWaySimulationInterface;
import com.ruoyi.gateway.server.protocol.ServerInitiativeProtocol;
import com.ruoyi.simulation.model.SimulationDeviceRequestTemplate;
import org.springframework.stereotype.Component;

/**
 * @auther: KUN
 * @date: 2023/3/28
 * @description: modbus模拟设备实现类
 */
@Component("modbusTcp")
public class ModbusSimulationInterfaceImpl extends AbstractGateWaySimulationInterface<ModbusMethodEnum, ModbusTcpForDtuCommonProtocol, ModbusTcpForDtuMessage> {


    private final String packageName = "com.ruoyi.gateway.modbus";

    public ModbusSimulationInterfaceImpl() {
    }

    @Override
    protected String checkPackageName() {
        return packageName;
    }

    /**
     * 处理请求的返回值
     *
     * @param result
     */
    @Override
    public void handlerResponse(SimulationDeviceRequestTemplate template, Object result) {

        if ((result instanceof ServerInitiativeProtocol)) {
            ServerInitiativeProtocol protocol = (ServerInitiativeProtocol) result;
            protocol.request(p->{
                System.out.println("收到消息");
            });

        } else {
            System.out.println("未成功");
        }

    }


    @Override
    public String getSimulationName() {
        return "modbusTcp";
    }
}
