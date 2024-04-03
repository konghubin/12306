package com.hubindeveloper.train.batch.controller;

import com.hubindeveloper.train.batch.feign.BusinessFeign;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description：
 * @author：Kong
 * @date：2024/2/6
 */
@RestController
public class TestController {
    @Resource
    BusinessFeign businessFeign;

    @GetMapping("/hello")
    public String hello(){
        String hello = businessFeign.hello();
        return hello;
    }
}
