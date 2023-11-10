package com.ruoyi.gateway.client.udp;

import com.ruoyi.gateway.client.ClientConnectProperties;

import java.net.InetSocketAddress;

/**
 * Udp协议客户端连接配置
 */
public class UdpClientConnectProperties extends ClientConnectProperties {

    private InetSocketAddress sender;
    private InetSocketAddress recipient;

    public UdpClientConnectProperties() { }

    /**
     * @param recipientHost 远程接收方主机地址
     * @param recipientPort 远程接收方主机端口
     */
    public UdpClientConnectProperties(String recipientHost, Integer recipientPort) {
        super(recipientHost, recipientPort);
        this.recipient = new InetSocketAddress(recipientHost, recipientPort);
    }

    /**
     * @param recipientHost 远程接收方主机地址
     * @param recipientPort 远程接收方主机端口
     * @param senderHost 本地发送方主机地址
     * @param senderPort 本地发送方主机端口
     */
    public UdpClientConnectProperties(String recipientHost, Integer recipientPort, String senderHost, Integer senderPort) {
        super(recipientHost, recipientPort, senderHost, senderPort);
        this.sender = new InetSocketAddress(senderHost, senderPort);
        this.recipient = new InetSocketAddress(recipientHost, recipientPort);
    }

    public InetSocketAddress getSender() {
        if(this.sender == null && this.getLocalHost() != null && this.getLocalPort() != null) {
            this.sender = new InetSocketAddress(this.getLocalHost(), this.getLocalPort());
        }

        return sender;
    }

    public void setSender(InetSocketAddress sender) {
        this.sender = sender;
    }

    public InetSocketAddress getRecipient() {
        if(this.recipient == null) {
            this.recipient = new InetSocketAddress(this.getHost(), this.getPort());
        }

        return recipient;
    }

    public void setRecipient(InetSocketAddress recipient) {
        this.recipient = recipient;
    }
}
