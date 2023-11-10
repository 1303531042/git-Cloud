package com.ruoyi.gateway.client.codec;

import com.ruoyi.gateway.client.ClientConnectProperties;
import com.ruoyi.gateway.client.TcpSocketClient;
import com.ruoyi.gateway.client.component.TcpClientComponent;
import com.ruoyi.gateway.codec.adapter.FixedLengthFrameDecoderAdapter;
import io.netty.channel.ChannelInboundHandler;

public class FixedLengthFrameClient extends TcpSocketClient {

    private int frameLength;

    public FixedLengthFrameClient(TcpClientComponent clientComponent, ClientConnectProperties config, int frameLength) {
        super(clientComponent, config);
        this.frameLength = frameLength;
    }

    @Override
    protected ChannelInboundHandler createProtocolDecoder() {
        return new FixedLengthFrameDecoderAdapter(frameLength);
    }

    public int getFrameLength() {
        return frameLength;
    }

    public void setFrameLength(int frameLength) {
        this.frameLength = frameLength;
    }
}
