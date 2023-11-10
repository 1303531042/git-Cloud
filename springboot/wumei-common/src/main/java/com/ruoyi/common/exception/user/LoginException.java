package com.ruoyi.common.exception.user;


public class LoginException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private Integer code;  //异常对应的返回码

    private String message;

    public LoginException(String message) {
        super(message);
    }

    /**
     * 空构造方法，避免反序列化问题
     */
    public LoginException() {
    }

    public LoginException(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}