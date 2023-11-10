package com.ruoyi.gateway.client.codec;

import com.ruoyi.gateway.CoreConst;
import com.ruoyi.gateway.client.TcpSocketClient;
import com.ruoyi.gateway.client.websocket.WebSocketClientComponentAbstract;
import com.ruoyi.gateway.client.websocket.WebSocketClientConnectProperties;
import com.ruoyi.gateway.websocket.WebSocketEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.http.HttpClientCodec;

import java.util.List;

import static com.ruoyi.gateway.CoreConst.CLIENT_ENCODER_HANDLER;

public class WebSocketClient extends TcpSocketClient {

    public WebSocketClient(WebSocketClientComponentAbstract clientComponent, WebSocketClientConnectProperties config) {
        super(clientComponent, config);
    }

    @Override
    protected ChannelInboundHandler createProtocolDecoder() {
        return new WebSocketClientDecoder(this);
    }

    @Override
    protected void doInitChannel(Channel channel) {
        super.doInitChannel(channel);
        // 替换掉编码器
        channel.pipeline().replace(CLIENT_ENCODER_HANDLER, CLIENT_ENCODER_HANDLER, getClientComponent().getWebSocketEncoder());
        channel.pipeline().addFirst(new HttpClientCodec());
    }

    @Override
    public WebSocketClientConnectProperties getConfig() {
        return (WebSocketClientConnectProperties) super.getConfig();
    }

    @Override
    public WebSocketClientComponentAbstract getClientComponent() {
        return (WebSocketClientComponentAbstract) super.getClientComponent();
    }

    @Override
    public synchronized void reconnection() {
        Object o = getChannel().attr(CoreConst.WEBSOCKET_CLOSE).get();
        /**
         * 属于关闭请求, 直接移除客户端
         * @see WebSocketEncoder#encode(ChannelHandlerContext, Object, List)
         */
        if(o instanceof Boolean) {
            this.disconnect(true);
        } else {
            super.reconnection();
        }
    }
}
