package com.hubindeveloper.train.member.controller.admin;

import com.hubindeveloper.train.common.context.LoginMemberContext;
import com.hubindeveloper.train.common.resp.CommonResp;
import com.hubindeveloper.train.common.resp.PageResp;
import com.hubindeveloper.train.member.req.TicketQueryReq;
import com.hubindeveloper.train.member.req.TicketSaveReq;
import com.hubindeveloper.train.member.resp.TicketQueryResp;
import com.hubindeveloper.train.member.service.TicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/ticket")
public class TicketAdminController {

    @Resource
    private TicketService ticketService;
    @GetMapping("/query-list")
    public CommonResp<PageResp<TicketQueryResp>> queryList(@Valid TicketQueryReq req) {
        PageResp<TicketQueryResp> list = ticketService.queryList(req);
        return new CommonResp<>(list);
    }
}
