package com.hubindeveloper.train.member.controller;

import com.hubindeveloper.train.common.context.LoginMemberContext;
import com.hubindeveloper.train.common.resp.CommonResp;
import com.hubindeveloper.train.common.resp.PageResp;
import com.hubindeveloper.train.member.req.TicketQueryReq;
import com.hubindeveloper.train.member.resp.TicketQueryResp;
import com.hubindeveloper.train.member.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/query-list")
    public CommonResp<PageResp<TicketQueryResp>> query(@Valid TicketQueryReq req) {
        CommonResp<PageResp<TicketQueryResp>> commonResp = new CommonResp<>();
        req.setMemberId(LoginMemberContext.getId());
        PageResp<TicketQueryResp> pageResp = ticketService.queryList(req);
        commonResp.setContent(pageResp);
        return commonResp;
    }

}
