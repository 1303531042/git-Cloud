package com.ruoyi.gateway.client.component;

import com.ruoyi.gateway.client.ClientConnectProperties;
import com.ruoyi.gateway.client.ClientMessage;
import com.ruoyi.gateway.client.MultiClientManager;
import com.ruoyi.gateway.client.TcpSocketClient;
import com.ruoyi.gateway.client.codec.DelimiterBasedFrameClient;
import io.netty.buffer.ByteBuf;

public abstract class DelimiterBasedFrameClientComponent<M extends ClientMessage> extends TcpClientComponent<M> {

    private int maxFrameLength;
    private boolean stripDelimiter;
    private boolean failFast;
    private ByteBuf[] delimiter;

    public DelimiterBasedFrameClientComponent(ClientConnectProperties config, int maxFrameLength, ByteBuf delimiter) {
        this(config, maxFrameLength, true, true, new ByteBuf[]{delimiter});
    }

    public DelimiterBasedFrameClientComponent(ClientConnectProperties config
            , int maxFrameLength, boolean stripDelimiter, boolean failFast, ByteBuf... delimiter) {
        super(config);
        this.failFast = failFast;
        this.delimiter = delimiter;
        this.maxFrameLength = maxFrameLength;
        this.stripDelimiter = stripDelimiter;
    }

    public DelimiterBasedFrameClientComponent(ClientConnectProperties config, MultiClientManager clientManager
            , int maxFrameLength, boolean stripDelimiter, boolean failFast, ByteBuf... delimiter) {
        super(config, clientManager);
        this.failFast = failFast;
        this.delimiter = delimiter;
        this.maxFrameLength = maxFrameLength;
        this.stripDelimiter = stripDelimiter;
    }

    @Override
    public TcpSocketClient createNewClient(ClientConnectProperties config) {
        return new DelimiterBasedFrameClient(this, config
                , this.getMaxFrameLength(), isStripDelimiter(), this.isFailFast(), this.getDelimiter());
    }

    public int getMaxFrameLength() {
        return maxFrameLength;
    }

    public void setMaxFrameLength(int maxFrameLength) {
        this.maxFrameLength = maxFrameLength;
    }

    public boolean isStripDelimiter() {
        return stripDelimiter;
    }

    public void setStripDelimiter(boolean stripDelimiter) {
        this.stripDelimiter = stripDelimiter;
    }

    public boolean isFailFast() {
        return failFast;
    }

    public void setFailFast(boolean failFast) {
        this.failFast = failFast;
    }

    public ByteBuf[] getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(ByteBuf[] delimiter) {
        this.delimiter = delimiter;
    }
}
