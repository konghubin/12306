package com.hubindeveloper.train.common.exception;

import lombok.Data;

/**
 * @description：自定义异常枚举类
 * @author：Kong
 * @date：2024/2/6
 */
public enum BusinessExceptionEnum {
    MEMBER_MOBILE_EXIST("手机号已注册！"),
    MEMBER_MOBILE_NOT_EXIST("请先获取短信验证码！"),
    MEMBER_MOBILE_CODE_ERROR("短信验证码错误！");


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
