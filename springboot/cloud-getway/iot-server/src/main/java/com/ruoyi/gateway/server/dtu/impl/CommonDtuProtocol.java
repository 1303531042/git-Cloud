package com.ruoyi.gateway.server.dtu.impl;

import com.ruoyi.gateway.protocol.ProtocolType;
import com.ruoyi.gateway.consts.ExecStatus;
import com.ruoyi.gateway.message.DefaultMessageHead;
import com.ruoyi.gateway.server.dtu.DtuCommonProtocolType;
import com.ruoyi.gateway.server.protocol.ServerInitiativeSyncProtocol;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 使用同步请求协议
 */
public class CommonDtuProtocol extends ServerInitiativeSyncProtocol<CommonDtuServerMessage> {

    private String deviceSn;
    private byte[] message;

    public CommonDtuProtocol(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    /**
     * 读设备数据
     * @param cmd 读指令
     */
    public byte[] read(byte[] cmd) {
        this.message = cmd;
        this.request();
        if(getExecStatus() == ExecStatus.success) {
            return this.responseMessage().getMessage();
        } else {
            return null;
        }
    }

    /**
     * 异步写指令到设备<hr>
     *     用于设备对写指令不响应的情况
     * @see #setDownLatch(CountDownLatch)
     * @see #isRelation()
     * @param cmd
     * @return 报文写出状态
     */
    public ExecStatus writeOfAsync(byte[] cmd) {
        this.message = cmd;
        this.setDownLatch(null);
        this.request();
        return this.getExecStatus();
    }

    /**
     * 写指令到设备, 并且等待设备响应
     * @param cmd
     * @return
     */
    public byte[] write(byte[] cmd) {
        this.message = cmd;
        this.request();
        if(getExecStatus() == ExecStatus.success) {
            return this.responseMessage().getMessage();
        } else {
            return null;
        }
    }

    @Override
    public CommonDtuProtocol sync(long timeout) {
        return super.sync(timeout);
    }

    @Override
    public CommonDtuProtocol timeout(long timeout) {
        return (CommonDtuProtocol) super.timeout(timeout);
    }

    @Override
    protected CommonDtuServerMessage doBuildRequestMessage() throws IOException {
        DefaultMessageHead messageHead = new DefaultMessageHead(this.deviceSn, this.deviceSn, protocolType());
        messageHead.setMessage(this.getMessage());
        return new CommonDtuServerMessage(messageHead);
    }

    @Override
    protected void doBuildResponseMessage(CommonDtuServerMessage message) { }

    @Override
    public boolean isRelation() {
        if(this.isSyncRequest()) {
            return super.isRelation();
        } else {
            return false;
        }
    }

    @Override
    public ProtocolType protocolType() {
        return DtuCommonProtocolType.COMMON;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }
}
