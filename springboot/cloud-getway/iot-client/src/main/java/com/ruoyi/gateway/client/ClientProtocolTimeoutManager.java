package com.ruoyi.gateway.client;

import com.ruoyi.gateway.IotThreadManager;
import com.ruoyi.gateway.client.proxy.ProxyClientProtocol;
import com.ruoyi.gateway.client.protocol.ClientInitiativeProtocol;
import com.ruoyi.gateway.AbstractProtocolTimeoutManager;
import com.ruoyi.gateway.protocol.Protocol;
import com.ruoyi.gateway.ProtocolTimeoutStorage;
import com.ruoyi.gateway.consts.ExecStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * 用来管理{@link ClientInitiativeProtocol#relationKey()}
 */
public class ClientProtocolTimeoutManager extends AbstractProtocolTimeoutManager {

    @Autowired
    private IotThreadManager threadManager;

    public ClientProtocolTimeoutManager(List<ProtocolTimeoutStorage> timeoutStorages) {
        super(timeoutStorages);
    }

    public ClientProtocolTimeoutManager(List<ProtocolTimeoutStorage> timeoutStorages, Executor executor) {
        super(timeoutStorages, executor);
    }

    @Override
    protected String protocolRemoveHandle(Protocol protocol) {
        try {
            if(protocol instanceof ClientInitiativeProtocol) {
                // 此协议超时
                ((ClientInitiativeProtocol<?>) protocol).setExecStatus(ExecStatus.timeout);

                if(protocol instanceof ProxyClientProtocol) {
                    ((ProxyClientProtocol) protocol).setReason("服务端响应超时("+((ProxyClientProtocol) protocol).getTimeout()+"ms)");
                }

                return protocol.getEquipCode();
            }

            return null;
        } finally {

            if(protocol instanceof ClientInitiativeProtocol) {
                // 同步请求释放锁
                if(((ClientInitiativeProtocol<?>) protocol).isSyncRequest()) {
                    ((ClientInitiativeProtocol<?>) protocol).releaseLock(); // 释放锁
                } else {
                    // 异步请求则将业务交由工作组线程处理
                    threadManager.getWorkerGroup().next().execute(() -> {
                        // 执行业务
                        ((ClientInitiativeProtocol<?>) protocol).exec(IotClientBootstrap.businessFactory);
                    });
                }
            }
        }
    }
}
