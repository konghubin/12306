package com.hubindeveloper.train.member.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description：会员注册请求实体类
 * @author：Kong
 * @date：2024/2/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterReq {
    @NotBlank(message = "手机号不能为空！")
    private String mobile;
}
