package com.hubindeveloper.train.business.req;

import com.hubindeveloper.train.common.req.PageReq;
import lombok.Data;

@Data
public class DailyTrainSeatQueryReq extends PageReq {

    private String trainCode;
}
