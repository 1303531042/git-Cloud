package com.ruoyi.gateway.client.codec;

import com.ruoyi.gateway.client.ClientConnectProperties;
import com.ruoyi.gateway.client.TcpSocketClient;
import com.ruoyi.gateway.client.component.TcpClientComponent;
import com.ruoyi.gateway.codec.adapter.LineBasedFrameMessageDecoderAdapter;
import io.netty.channel.ChannelInboundHandler;

public class LineBaseFrameClient extends TcpSocketClient {

    private int maxLength;
    private boolean failFast;
    private boolean stripDelimiter;

    public LineBaseFrameClient(TcpClientComponent clientComponent, ClientConnectProperties config, int maxLength) {
        this(clientComponent, config, maxLength, false, true);
    }

    public LineBaseFrameClient(TcpClientComponent clientComponent, ClientConnectProperties config
            , int maxLength, boolean failFast, boolean stripDelimiter) {
        super(clientComponent, config);
        this.failFast = failFast;
        this.maxLength = maxLength;
        this.stripDelimiter = stripDelimiter;
    }

    @Override
    protected ChannelInboundHandler createProtocolDecoder() {
        return new LineBasedFrameMessageDecoderAdapter(this.maxLength, stripDelimiter, failFast);
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
