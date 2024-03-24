package com.hubindeveloper.train.common.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description：分页返回父类
 * @author：Kong
 * @date：2024/3/24
 */
@Data
public class PageResp<T> implements Serializable {
    private long total;

    private List<T> list;
}
