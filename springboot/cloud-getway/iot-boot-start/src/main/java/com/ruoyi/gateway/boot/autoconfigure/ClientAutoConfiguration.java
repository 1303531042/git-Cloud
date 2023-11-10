package com.ruoyi.gateway.boot.autoconfigure;

import com.ruoyi.gateway.IotThreadManager;
import com.ruoyi.gateway.client.ClientProperties;
import com.ruoyi.gateway.client.IotClientBootstrap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(name = "com.ruoyi.gateway.client.IotClientBootstrap")
public class ClientAutoConfiguration {

    @Configuration
    @EnableConfigurationProperties(ClientProperties.class)
    public static class IotClientAutoConfiguration extends IotClientBootstrap {

        public IotClientAutoConfiguration(IotThreadManager threadManager, ClientProperties properties) {
            super(threadManager, properties);
        }
    }
}
