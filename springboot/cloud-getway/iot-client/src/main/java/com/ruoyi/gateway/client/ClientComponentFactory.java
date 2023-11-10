package com.ruoyi.gateway.client;

import com.ruoyi.gateway.IotThreadManager;
import com.ruoyi.gateway.client.component.SingleTcpClientComponent;
import com.ruoyi.gateway.client.component.SocketClientComponent;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientComponentFactory implements InitializingBean {

    private IotThreadManager threadManager;
    private List<ClientComponent> clientComponents = new ArrayList<>();
    private Map<Class<? extends ClientMessage>, ClientComponent> messageComponentMap = new HashMap(8);

    public ClientComponentFactory(IotThreadManager threadManager, List<ClientComponent> clientComponents) {
        this.threadManager = threadManager;
        // https://gitee.com/iteaj/iot/issues/I5BWTA
        if(!CollectionUtils.isEmpty(clientComponents)) {
            this.clientComponents = clientComponents;
        }
    }

    public List<ClientComponent> getComponents() {
        return this.clientComponents;
    }

    public ClientComponent getByClass(Class<? extends ClientMessage> messageClass) {
        return messageComponentMap.get(messageClass);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(!CollectionUtils.isEmpty(clientComponents)) {
            for(int i=0; i<clientComponents.size(); i++) {
                ClientComponent clientComponent = clientComponents.get(i);
                if(clientComponent instanceof SocketClientComponent) {
                    clientComponent.init(threadManager.getWorkerGroup());
                } else if(clientComponent instanceof SingleTcpClientComponent) {
                    clientComponent.init(threadManager.getWorkerGroup());
                } else {
                    clientComponent.init();
                }

                messageComponentMap.put(clientComponent.getMessageClass(), clientComponent);
            }
        }
    }

}
