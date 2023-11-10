package com.ruoyi.gateway.server;

import com.ruoyi.gateway.config.ConnectProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "iot.server")
public class IotServerProperties {

    /**
     * websocket服务端配置
     */
    private WebSocketConnectProperties websocket;

    /**
     * 代理服务配置信息
     */
    private ProxyServerConnectProperties proxy = new ProxyServerConnectProperties();

    public ProxyServerConnectProperties getProxy() {
        return proxy;
    }

    public IotServerProperties setProxy(ProxyServerConnectProperties proxy) {
        this.proxy = proxy;
        return this;
    }

    public static class ProxyServerConnectProperties extends ConnectProperties {

        /**
         * 是否启用客户端端口监听
         */
        private boolean start = true;

        public ProxyServerConnectProperties() {
            super(30168);
        }

        public boolean isStart() {
            return start;
        }

        public void setStart(boolean start) {
            this.start = start;
        }
    }

    public static class WebSocketConnectProperties extends ConnectProperties {
        /**
         * 是否启用WebSocket端口(默认8088)
         */
        private boolean start = false;

        public WebSocketConnectProperties() {
            super(8088);
        }

        public boolean isStart() {
            return start;
        }

        public void setStart(boolean start) {
            this.start = start;
        }
    }

    public WebSocketConnectProperties getWebsocket() {
        return websocket;
    }

    public void setWebsocket(WebSocketConnectProperties websocket) {
        this.websocket = websocket;
    }
}
