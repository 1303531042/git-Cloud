package com.ruoyi.gateway.server.dtu;

import com.ruoyi.gateway.protocol.ProtocolType;

public enum DtuCommonProtocolType implements ProtocolType {

    AT("AT指令"),
    DTU("DTU私有协议"),
    COMMON("通用DTU协议"),
    HEARTBEAT("DTU心跳"),
    PASSED("交由下个处理器"),
    DEVICE_SN("DTU注册编号")
    ;

    private String desc;

    DtuCommonProtocolType(String desc) {
        this.desc = desc;
    }

    @Override
    public Enum getType() {
        return this;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
