package com.ruoyi.gateway;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * create time: 2022/1/17
 * 框架准备就绪事件监听
 * @author iteaj
 * @since 1.0
 */
public class IotFrameworkReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        final ConfigurableApplicationContext context = event.getApplicationContext();
        final String version = context.getEnvironment().getProperty("iot.version");
        System.out.println("   ===   --------------------------------------------------------------------------------   =======\n"
                +"    |    |                                                                              |      |\n"
                +"    |    |                     STARTED IOT FRAMEWORK BY NETTY (v"+version+")                  |      |\n"
                +"    |    |                             (welcome to apply IOT)                           |      |\n"
                +"    |    |                                                                              |      |\n"
                +"   ===   --------------------------------------------------------------------------------      *");
    }
}
