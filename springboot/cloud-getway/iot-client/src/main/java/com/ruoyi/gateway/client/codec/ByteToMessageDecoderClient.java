package com.ruoyi.gateway.client.codec;

import com.ruoyi.gateway.client.ClientConnectProperties;
import com.ruoyi.gateway.client.TcpSocketClient;
import com.ruoyi.gateway.client.component.TcpClientComponent;
import com.ruoyi.gateway.codec.adapter.ByteToMessageDecoderAdapter;

public class ByteToMessageDecoderClient extends TcpSocketClient {

    public ByteToMessageDecoderClient(TcpClientComponent clientComponent, ClientConnectProperties config) {
        super(clientComponent, config);
    }

    @Override
    protected ByteToMessageDecoderAdapter createProtocolDecoder() {
        return new ByteToMessageDecoderAdapter(this.getClientComponent());
    }
}
