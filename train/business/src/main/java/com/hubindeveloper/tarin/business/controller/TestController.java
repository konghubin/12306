package com.hubindeveloper.tarin.business.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description：
 * @author：Kong
 * @date：2024/2/6
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }
}
