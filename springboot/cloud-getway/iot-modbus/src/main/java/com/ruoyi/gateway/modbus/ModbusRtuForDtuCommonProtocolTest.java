package com.ruoyi.gateway.modbus;

import com.ruoyi.gateway.modbus.server.dtu.ModbusRtuForDtuCommonProtocol;
import com.ruoyi.gateway.modbus.server.dtu.ModbusTcpForDtuCommonProtocol;
import com.ruoyi.simulation.manager.SimulationInterfaceManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @auther: KUN
 * @date: 2023/5/22
 * @description:
 */
@Component
public class ModbusRtuForDtuCommonProtocolTest implements ApplicationRunner, BeanFactoryAware {
    private ListableBeanFactory beanFactory;
     @Override
    public void run(ApplicationArguments args) throws Exception {
//        test();
    }

//    @Scheduled(cron = "0 0/1 * * * ?")
    public void test() {
        ModbusRtuForDtuCommonProtocol modbusRtuForDtuCommonProtocol =
                ModbusRtuForDtuCommonProtocol.buildRead03("www.usr.cn", 1, 256, 2);
        modbusRtuForDtuCommonProtocol.request(
                (p->{
                    ModbusRtuForDtuCommonProtocol protocol = (ModbusRtuForDtuCommonProtocol) p;
                    ReadPayload payload = (ReadPayload) protocol.getPayload();
                    float v = payload.readFloat(256);
                    System.out.println("收到消息");
                })
        );

//        ModbusTcpForDtuCommonProtocol modbusTcpForDtuCommonProtocol = ModbusTcpForDtuCommonProtocol.buildRead03("www.usr.cn", 1, 256, 2);
//        modbusTcpForDtuCommonProtocol.request(
//                (p->{
//                    ModbusTcpForDtuCommonProtocol protocol = (ModbusTcpForDtuCommonProtocol) p;
//                    ReadPayload payload = (ReadPayload) protocol.getPayload();
//                    float v = payload.readFloat(256);
//                    System.out.println("收到消息");
//                })
//        );
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }
}
