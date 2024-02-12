package com.hubindeveloper.train.member.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description：新增乘车人请求
 * @author：Kong
 * @date：2024/2/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerSaveReq {
    private Long id;

    @NotNull(message = "会员ID不能为空！")
    private Long memberId;

    @NotBlank(message = "姓名不能为空！")
    private String name;

    @NotBlank(message = "身份证号不能为空！")
    private String idCard;

    @NotBlank(message = "旅客类型不能为空！")
    private String type;

    private Date createTime;

    private Date updateTime;
}
