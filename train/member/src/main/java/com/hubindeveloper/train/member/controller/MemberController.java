package com.hubindeveloper.train.member.controller;

import com.hubindeveloper.train.member.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
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
    public Integer count(){
        return memberService.count();
    }
}
