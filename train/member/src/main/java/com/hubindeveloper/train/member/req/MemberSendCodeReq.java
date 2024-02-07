package com.hubindeveloper.train.member.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description：登录注册发送验证码
 * @author：Kong
 * @date：2024/2/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSendCodeReq {
    @NotBlank(message = "手机号不能为空！")
    @Pattern(regexp = "^1\\d{10}$", message = "手机号码格式错误")
    private String mobile;
}
