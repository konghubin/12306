package com.hubindeveloper.train.member.req;

import com.hubindeveloper.train.common.req.PageReq;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description：乘客查询请求
 * @author：Kong
 * @date：2024/2/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerQueryReq extends PageReq {
    private Long memberId;

}
