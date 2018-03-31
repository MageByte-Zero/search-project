package com.zero.common.exception;

/**
 * 自定义业务异常，持有异常枚举
 */
public class ServiceException extends RuntimeException {
    private ResponseEnum responseEnum;

    public ServiceException() {
        super();
    }
    public ServiceException(ResponseEnum responseEnum) {
        this.responseEnum = responseEnum;
    }

    public ServiceException(String message, ResponseEnum responseEnum) {
        super(message);
        this.responseEnum = responseEnum;
    }

    public ServiceException(String message, Throwable cause, ResponseEnum responseEnum) {
        super(message, cause);
        this.responseEnum = responseEnum;
    }

    public ServiceException(Throwable cause, ResponseEnum responseEnum) {
        super(cause);
        this.responseEnum = responseEnum;
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }


    public ResponseEnum getResponseEnum() {
        return responseEnum;
    }

    public void setResponseEnum(ResponseEnum responseEnum) {
        this.responseEnum = responseEnum;
    }
}
