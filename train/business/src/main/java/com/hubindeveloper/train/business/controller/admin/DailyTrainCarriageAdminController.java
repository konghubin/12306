package com.hubindeveloper.train.business.controller.admin;

import com.hubindeveloper.train.common.context.LoginMemberContext;
import com.hubindeveloper.train.common.resp.CommonResp;
import com.hubindeveloper.train.common.resp.PageResp;
import com.hubindeveloper.train.business.req.DailyTrainCarriageQueryReq;
import com.hubindeveloper.train.business.req.DailyTrainCarriageSaveReq;
import com.hubindeveloper.train.business.resp.DailyTrainCarriageQueryResp;
import com.hubindeveloper.train.business.service.DailyTrainCarriageService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-carriage")
public class DailyTrainCarriageAdminController {

    @Resource
    private DailyTrainCarriageService dailyTrainCarriageService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody DailyTrainCarriageSaveReq req) {
        dailyTrainCarriageService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainCarriageQueryResp>> queryList(@Valid DailyTrainCarriageQueryReq req) {
        PageResp<DailyTrainCarriageQueryResp> list = dailyTrainCarriageService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        dailyTrainCarriageService.delete(id);
        return new CommonResp<>();
    }

}
