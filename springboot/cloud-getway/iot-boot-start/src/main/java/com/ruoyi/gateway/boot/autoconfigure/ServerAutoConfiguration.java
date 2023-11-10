package com.ruoyi.gateway.boot.autoconfigure;

import com.ruoyi.gateway.IotCoreProperties;
import com.ruoyi.gateway.IotThreadManager;
import com.ruoyi.gateway.server.IotServeBootstrap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(name = "com.ruoyi.gateway.server.IotServeBootstrap")
public class ServerAutoConfiguration {

    @Configuration
    public static class IotServerAutoConfiguration extends IotServeBootstrap {

        public IotServerAutoConfiguration(ApplicationContext applicationContext
                , IotThreadManager threadManager, IotCoreProperties coreProperties) {
            super(applicationContext, threadManager, coreProperties);
        }
    }
}
