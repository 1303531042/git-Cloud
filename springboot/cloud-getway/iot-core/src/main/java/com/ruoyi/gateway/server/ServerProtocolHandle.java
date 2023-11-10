package com.ruoyi.gateway.server;

import com.ruoyi.gateway.ProtocolHandle;
import org.springframework.core.GenericTypeResolver;

public interface ServerProtocolHandle<T extends ServerSocketProtocol> extends ProtocolHandle<T> {

    default Class<T> protocolClass() {
        return (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), ServerProtocolHandle.class);
    }
}
