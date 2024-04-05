package com.hubindeveloper.train.business.controller;

import com.hubindeveloper.train.business.req.ConfirmOrderDoReq;
import com.hubindeveloper.train.business.req.ConfirmOrderQueryReq;
import com.hubindeveloper.train.business.resp.ConfirmOrderQueryResp;
import com.hubindeveloper.train.business.service.ConfirmOrderService;
import com.hubindeveloper.train.common.resp.CommonResp;
import com.hubindeveloper.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/confirm-order")
public class ConfirmOrderController {

    @Resource
    private ConfirmOrderService confirmOrderService;

    @PostMapping("/do")
    public CommonResp<Object> doConfirm(@Valid @RequestBody ConfirmOrderDoReq req) {
        confirmOrderService.doConfirm(req);
        return new CommonResp<>();
    }
}
