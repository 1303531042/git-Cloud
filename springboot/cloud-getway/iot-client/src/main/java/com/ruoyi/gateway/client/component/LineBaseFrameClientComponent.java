package com.ruoyi.gateway.client.component;

import com.ruoyi.gateway.client.ClientConnectProperties;
import com.ruoyi.gateway.client.ClientMessage;
import com.ruoyi.gateway.client.MultiClientManager;
import com.ruoyi.gateway.client.TcpSocketClient;
import com.ruoyi.gateway.client.codec.LineBaseFrameClient;

public abstract class LineBaseFrameClientComponent<M extends ClientMessage> extends TcpClientComponent<M> {

    private int maxLength;
    private boolean failFast;
    private boolean stripDelimiter;

    public LineBaseFrameClientComponent(ClientConnectProperties config) {
        super(config);
    }

    public LineBaseFrameClientComponent(ClientConnectProperties config, int maxLength) {
        super(config);
        this.maxLength = maxLength;
    }

    public LineBaseFrameClientComponent(ClientConnectProperties config, int maxLength, boolean stripDelimiter, boolean failFast) {
        super(config);
        this.failFast = failFast;
        this.maxLength = maxLength;
        this.stripDelimiter = stripDelimiter;
    }

    public LineBaseFrameClientComponent(ClientConnectProperties config, MultiClientManager clientManager
            , int maxLength, boolean stripDelimiter, boolean failFast) {
        super(config, clientManager);
        this.failFast = failFast;
        this.maxLength = maxLength;
        this.stripDelimiter = stripDelimiter;
    }

    @Override
    public TcpSocketClient createNewClient(ClientConnectProperties config) {
        return new LineBaseFrameClient(this, config, this.getMaxLength(), this.isFailFast(), isStripDelimiter());
    }

    public int getMaxLength() {
        return maxLength;
    }

    public boolean isFailFast() {
        return failFast;
    }

    public boolean isStripDelimiter() {
        return stripDelimiter;
    }
}
