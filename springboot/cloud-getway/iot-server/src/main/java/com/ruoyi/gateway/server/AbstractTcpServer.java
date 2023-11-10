package com.ruoyi.gateway.server;

import com.ruoyi.gateway.config.ConnectProperties;

public abstract class AbstractTcpServer implements IotSocketServer {

    private int port;
    private ConnectProperties config;

    /**
     * @see #AbstractTcpServer(ConnectProperties)
     * @param port
     */
    @Deprecated
    public AbstractTcpServer(int port) {
        this(new ConnectProperties(port));
    }

    public AbstractTcpServer(ConnectProperties serverConfig) {
        this.config = serverConfig;
        this.port = serverConfig.getPort();
    }

    @Override
    public int port() {
        return this.port;
    }

    @Override
    public ConnectProperties config() {
        return this.config;
    }

}
