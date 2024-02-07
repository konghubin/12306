package com.hubindeveloper.train.member.controller;

import com.hubindeveloper.train.common.resp.CommonResp;
import com.hubindeveloper.train.member.req.MemberLoginReq;
import com.hubindeveloper.train.member.req.MemberRegisterReq;
import com.hubindeveloper.train.member.req.MemberSendCodeReq;
import com.hubindeveloper.train.member.resp.MemberLoginResp;
import com.hubindeveloper.train.member.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description：
 * @author：Kong
 * @date：2024/2/6
 */
@RestController
public class MemberController {
    @Resource
    private MemberService memberService;

    @GetMapping("/count")
    public CommonResp<Integer> count(){
        int count = memberService.count();
        CommonResp<Integer> commonResp = new CommonResp<>();
        commonResp.setContent(count);
        return commonResp;
    }

    @PostMapping("/register")
    public CommonResp<Long> register(@Valid MemberRegisterReq req){
        long registerId = memberService.register(req);
        CommonResp<Long> commonResp = new CommonResp<>();
        commonResp.setContent(registerId);
        return commonResp;
    }

    @PostMapping("/send-code")
    public CommonResp<Long> sendCode(@Valid MemberSendCodeReq req){
        memberService.sendCode(req);
        return new CommonResp<>();
    }

    @PostMapping("/login")
    public CommonResp<MemberLoginResp> login(@Valid MemberLoginReq req){
        MemberLoginResp resp = memberService.login(req);
        return new CommonResp<>(resp);
    }
}
