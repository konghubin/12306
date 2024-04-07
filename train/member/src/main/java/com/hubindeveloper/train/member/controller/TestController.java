package com.hubindeveloper.train.member.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description：
 * @author：Kong
 * @date：2024/2/6
 */
@RestController
@RefreshScope
public class TestController {
    @Value("${test.nacos}")
    private String name;
    @GetMapping("/hello")
    public String hello(){
        return "Hello " + name + "!";
    }
}
