package com.ruoyi.gateway.tools.db.rdbms;

import com.ruoyi.gateway.handle.proxy.ProtocolHandleProxy;
import com.ruoyi.gateway.tools.db.DBManager;
import com.ruoyi.gateway.tools.db.DBProtocolHandleProxyMatcher;

public class RdbmsHandleProxyMatcher implements DBProtocolHandleProxyMatcher {

    private final RdbmsSqlManager rdbmsSqlManager;

    public RdbmsHandleProxyMatcher(RdbmsSqlManager rdbmsSqlManager) {
        this.rdbmsSqlManager = rdbmsSqlManager;
    }

    @Override
    public boolean matcher(Object target) {
        return target instanceof RdbmsHandle;
    }

    @Override
    public DBManager getDbManager() {
        return rdbmsSqlManager;
    }

    @Override
    public Class<? extends ProtocolHandleProxy> getProxyClass() {
        return RdbmsHandle.class;
    }
}
