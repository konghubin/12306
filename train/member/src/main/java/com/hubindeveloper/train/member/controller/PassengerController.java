package com.hubindeveloper.train.member.controller;

import com.hubindeveloper.train.common.resp.CommonResp;
import com.hubindeveloper.train.member.req.PassengerSaveReq;
import com.hubindeveloper.train.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description：乘客人前端请求处理
 * @author：Kong
 * @date：2024/2/12
 */
@RestController
@RequestMapping("/passenger")
public class PassengerController {
    @Resource
    private PassengerService passengerService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody PassengerSaveReq req){
        passengerService.save(req);
        return new CommonResp<>();
    }
}
