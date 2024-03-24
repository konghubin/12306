package com.hubindeveloper.train.common.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @description：分页父类
 * @author：Kong
 * @date：2024/3/24
 */
@Data
public class PageReq {
    @NotNull(message = "页码不能为空！")
    private Integer page;

    @NotNull(message = "每条页数不能为空！")
    @Max(value = 100, message = "每条页数不能超过100！")
    private Integer size;
}
