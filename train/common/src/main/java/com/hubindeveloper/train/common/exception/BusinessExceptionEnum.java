package com.hubindeveloper.train.common.exception;

import lombok.Data;

/**
 * @description：自定义异常枚举类
 * @author：Kong
 * @date：2024/2/6
 */
public enum BusinessExceptionEnum {
    MEMBER_MOBILE_EXIST("手机号已注册！");

    private String desc;

    BusinessExceptionEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "BusinessExceptionEnum{" +
                "desc='" + desc + '\'' +
                '}';
    }
}
