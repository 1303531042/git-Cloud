package com.ruoyi.gateway.boot.autoconfigure.config;

import com.ruoyi.gateway.config.ConnectProperties;
import com.ruoyi.gateway.modbus.server.dtu.ModbusRtuForDtuServerComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther: KUN
 * @date: 2023/5/22
 * @description:
 */
@Configuration
public class ModbusRtuForDtuServerComponentConfiguration {
    @Bean
    public ModbusRtuForDtuServerComponent modbusTcpForDtuServerComponent() {
        // 监听7058端口
        return new ModbusRtuForDtuServerComponent(new ConnectProperties(7069));
    }
}
