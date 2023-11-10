package com.ruoyi.gateway.server;

import com.ruoyi.gateway.AbstractProtocolTimeoutManager;
import com.ruoyi.gateway.IotThreadManager;
import com.ruoyi.gateway.ProtocolPreservable;
import com.ruoyi.gateway.ProtocolTimeoutStorage;
import com.ruoyi.gateway.business.BusinessFactory;
import com.ruoyi.gateway.consts.ExecStatus;
import com.ruoyi.gateway.protocol.Protocol;
import com.ruoyi.gateway.server.protocol.ServerInitiativeProtocol;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * <p>用来管理{@link ProtocolPreservable#relationKey()}与{@link Protocol}的映射关系</p>
 * 在报文超时后会自动剔除超时的协议
 * Create Date By 2017-09-17
 * @author iteaj
 * @since 1.7
 */
public class ServerTimeoutProtocolManager extends AbstractProtocolTimeoutManager {

    private BusinessFactory businessFactory;
    private IotThreadManager iotThreadManager;

    public ServerTimeoutProtocolManager(BusinessFactory businessFactory
            , IotThreadManager threadManager, List<ProtocolTimeoutStorage> timeoutStorages) {
        super(timeoutStorages);
        this.iotThreadManager = threadManager;
        this.businessFactory = businessFactory;
    }

    public ServerTimeoutProtocolManager(BusinessFactory businessFactory, IotThreadManager threadManager
            , List<ProtocolTimeoutStorage> timeoutStorages, Executor executor) {
        super(timeoutStorages, executor);
        this.iotThreadManager = threadManager;
        this.businessFactory = businessFactory;
    }

    protected String protocolRemoveHandle(Protocol protocol) {
        if(protocol instanceof ServerInitiativeProtocol) {
           try {
               ((ServerInitiativeProtocol<?>) protocol).setExecStatus(ExecStatus.timeout);

               return protocol.getEquipCode();
           } finally {
               // 同步请求则释放锁 由调用线程继续执行业务
               if(((ServerInitiativeProtocol<?>) protocol).isSyncRequest()) {
                   ((ServerInitiativeProtocol<?>) protocol).releaseLock();
               } else {
                   // 异步请求使用工作线程执行业务
                   iotThreadManager.getWorkerGroup().next().execute(() -> {
                       // 执行业务
                       ((ServerInitiativeProtocol<?>) protocol).exec(this.businessFactory);
                   });
               }
           }
        }

        return null;
    }
}
