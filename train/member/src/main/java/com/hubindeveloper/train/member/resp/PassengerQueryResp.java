package com.hubindeveloper.train.member.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description：乘客查询返回类
 * @author：Kong
 * @date：2024/2/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerQueryResp {
    private String name;

    private String idCard;

    private String type;

    private Date createTime;

    private Date updateTime;
}
