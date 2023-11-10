package com.ruoyi.gateway.server.dtu.message;

public class DtuPassMessage extends DtuServerMessageAbstract{

    protected static DtuPassMessage passMessage = new DtuPassMessage();

    public static DtuPassMessage getInstance() {
        return passMessage;
    }

    protected DtuPassMessage() {
        super(EMPTY);
    }

    @Override
    protected MessageHead doBuild(byte[] message, String equipCode) {
        return null;
    }
}
