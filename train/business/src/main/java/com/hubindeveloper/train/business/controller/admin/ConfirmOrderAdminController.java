package com.hubindeveloper.train.business.controller.admin;

import com.hubindeveloper.train.common.context.LoginMemberContext;
import com.hubindeveloper.train.common.resp.CommonResp;
import com.hubindeveloper.train.common.resp.PageResp;
import com.hubindeveloper.train.business.req.ConfirmOrderQueryReq;
import com.hubindeveloper.train.business.req.ConfirmOrderSaveReq;
import com.hubindeveloper.train.business.resp.ConfirmOrderQueryResp;
import com.hubindeveloper.train.business.service.ConfirmOrderService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/confirm-order")
public class ConfirmOrderAdminController {

    @Resource
    private ConfirmOrderService confirmOrderService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody ConfirmOrderSaveReq req) {
        confirmOrderService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<ConfirmOrderQueryResp>> queryList(@Valid ConfirmOrderQueryReq req) {
        PageResp<ConfirmOrderQueryResp> list = confirmOrderService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        confirmOrderService.delete(id);
        return new CommonResp<>();
    }

}