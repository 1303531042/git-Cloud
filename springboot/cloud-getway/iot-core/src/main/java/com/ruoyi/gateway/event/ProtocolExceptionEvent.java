package com.ruoyi.gateway.event;

import com.ruoyi.gateway.protocol.Protocol;

public class ProtocolExceptionEvent extends IotFrameworkEvent{

    private Throwable cause;

    /**
     * Create AbstractQueryTemplateFluxWrapper new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public ProtocolExceptionEvent(Protocol source, Throwable cause) {
        super(source);
        this.cause = cause;
    }

    @Override
    public Protocol getSource() {
        return (Protocol) super.getSource();
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}
