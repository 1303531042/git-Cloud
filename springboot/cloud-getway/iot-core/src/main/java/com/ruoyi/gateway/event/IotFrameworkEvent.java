package com.ruoyi.gateway.event;

import com.ruoyi.gateway.FrameworkComponent;
import org.springframework.context.ApplicationEvent;

public class IotFrameworkEvent extends ApplicationEvent {

    private FrameworkComponent component;

    /**
     * Create AbstractQueryTemplateFluxWrapper new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public IotFrameworkEvent(Object source) {
        super(source);
    }

    public FrameworkComponent getComponent() {
        return component;
    }

    public IotFrameworkEvent setComponent(FrameworkComponent component) {
        this.component = component;
        return this;
    }
}
