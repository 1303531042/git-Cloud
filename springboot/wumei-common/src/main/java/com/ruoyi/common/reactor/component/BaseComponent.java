package com.ruoyi.common.reactor.component;


import com.ruoyi.common.reactor.EventChannel;
import com.ruoyi.common.reactor.EventChannelHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;

/**
 * @auther: KUN
 * @date: 2023/6/27
 * @description: 设备数据持久化组件
 */
public abstract class BaseComponent implements InitializingBean, BeanFactoryAware {
    @Setter
    @Getter
    private EventChannel eventChannel;
    @Setter
    @Getter
    private Integer ops;

    @Setter
    @Getter
    private Integer channelID;

    @Setter
    @Getter
    private String explain;

    @Getter
    @Setter
    protected String handlerName;

    @Setter
    @Getter
    private EventChannelHandler eventChannelHandler;

    @Getter
    private ListableBeanFactory beanFactory;

    public BaseComponent() {}

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setOps(registerOps());
        setExplain(registerExplain());
        setChannelID(registerChannelID());
        setHandlerName(registerHandlerName());
        setEventChannelHandler(beanFactory.getBean(getHandlerName(), EventChannelHandler.class));
        this.eventChannel = new EventChannel(getChannelID(), getExplain(), getEventChannelHandler());
        this.eventChannel.register(registerOps());

    }

    protected abstract int registerOps();

    protected abstract int registerChannelID();

    protected abstract String registerExplain();

    protected abstract String registerHandlerName();

}
