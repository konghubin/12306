package com.hubindeveloper.train.business.controller;

import com.hubindeveloper.train.business.req.TrainQueryReq;
import com.hubindeveloper.train.business.req.TrainSaveReq;
import com.hubindeveloper.train.business.resp.TrainQueryResp;
import com.hubindeveloper.train.business.service.TrainSeatService;
import com.hubindeveloper.train.business.service.TrainService;
import com.hubindeveloper.train.common.resp.CommonResp;
import com.hubindeveloper.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/train")
public class TrainController {
    @Resource
    private TrainService trainService;

    @GetMapping("/query-all")
    public CommonResp<List<TrainQueryResp>> queryAll() {
        List<TrainQueryResp> list = trainService.queryAll();
        return new CommonResp<>(list);
    }
}
