package com.hubindeveloper.train.common.exception;

/**
 * @description：自定义异常类
 * @author：Kong
 * @date：2024/2/6
 */
public class BusinessException extends RuntimeException{
    private BusinessExceptionEnum e;

    public BusinessException(BusinessExceptionEnum e) {
        this.e = e;
    }

    public BusinessExceptionEnum getE() {
        return e;
    }

    public void setE(BusinessExceptionEnum e) {
        this.e = e;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
