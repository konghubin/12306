package com.hubindeveloper.train.business.controller;

import com.hubindeveloper.train.business.req.StationQueryReq;
import com.hubindeveloper.train.business.req.StationSaveReq;
import com.hubindeveloper.train.business.resp.StationQueryResp;
import com.hubindeveloper.train.business.service.StationService;
import com.hubindeveloper.train.common.resp.CommonResp;
import com.hubindeveloper.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/station")
public class StationController {
    @Resource
    private StationService stationService;

    @GetMapping("/query-all")
    public CommonResp<List<StationQueryResp>> queryAll() {
        List<StationQueryResp> list = stationService.queryAll();
        return new CommonResp<>(list);
    }
}
