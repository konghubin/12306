package com.hubindeveloper.train.batch.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @description：定时任务测试类
 * @author：Kong
 * @date：2024/4/2
 */
@Component
@EnableScheduling
public class SpringbootTestJob {
    @Scheduled(cron = "0/5 * * * * ?")
    private void test(){
        System.out.println("定时任务测试");
    }
}
