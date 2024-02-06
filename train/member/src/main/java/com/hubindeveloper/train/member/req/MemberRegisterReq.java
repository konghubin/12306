package com.hubindeveloper.train.member.req;

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
    private String mobile;
}
