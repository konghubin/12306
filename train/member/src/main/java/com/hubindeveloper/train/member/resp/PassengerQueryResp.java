package com.hubindeveloper.train.member.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
