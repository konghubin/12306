package com.hubindeveloper.train.common.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description：登录返回给前端信息类
 * @author：Kong
 * @date：2024/2/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginResp {
    private long id;

    private String mobile;

    private String token;
}
