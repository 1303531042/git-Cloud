package com.ruoyi.gateway;

import com.ruoyi.gateway.handle.proxy.ProtocolHandleProxyPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;

public class SpringStartingListener implements ApplicationListener<ApplicationContextInitializedEvent> {

    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent applicationContextInitializedEvent) {
        final ConfigurableListableBeanFactory beanFactory = applicationContextInitializedEvent.getApplicationContext().getBeanFactory();
        beanFactory.addBeanPostProcessor(new ProtocolHandleProxyPostProcessor(beanFactory));
    }
}
