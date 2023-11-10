package com.ruoyi.gateway.client.component;

import com.ruoyi.gateway.client.ClientConnectProperties;
import com.ruoyi.gateway.client.ClientMessage;
import com.ruoyi.gateway.client.MultiClientManager;
import com.ruoyi.gateway.client.TcpSocketClient;
import com.ruoyi.gateway.client.codec.FixedLengthFrameClient;

/**
 * 使用固定长度解码器的客户端组件
 * @see FixedLengthFrameClient
 * @see io.netty.handler.codec.FixedLengthFrameDecoder
 * @param <M>
 */
public abstract class FixedLengthFrameClientComponent<M extends ClientMessage> extends TcpClientComponent<M> {

    private int frameLength;

    public FixedLengthFrameClientComponent(ClientConnectProperties config, int frameLength) {
        super(config);
        this.frameLength = frameLength;
    }

    public FixedLengthFrameClientComponent(ClientConnectProperties config, MultiClientManager clientManager, int frameLength) {
        super(config, clientManager);
        this.frameLength = frameLength;
    }

    @Override
    public TcpSocketClient createNewClient(ClientConnectProperties config) {
        return new FixedLengthFrameClient(this, config, this.frameLength);
    }

    public int getFrameLength() {
        return frameLength;
    }

    public void setFrameLength(int frameLength) {
        this.frameLength = frameLength;
    }
}
