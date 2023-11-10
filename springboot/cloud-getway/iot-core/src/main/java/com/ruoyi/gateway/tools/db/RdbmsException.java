package com.ruoyi.gateway.tools.db;

import com.ruoyi.gateway.tools.ToolsException;

public class RdbmsException extends ToolsException {

    public RdbmsException() { }

    public RdbmsException(String message) {
        super(message);
    }

    public RdbmsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RdbmsException(Throwable cause) {
        super(cause);
    }

    public RdbmsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
