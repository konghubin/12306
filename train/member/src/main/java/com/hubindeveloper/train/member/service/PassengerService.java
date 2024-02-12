package com.hubindeveloper.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.hubindeveloper.train.common.util.SnowUtil;
import com.hubindeveloper.train.member.domain.Passenger;
import com.hubindeveloper.train.member.mapper.PassengerMapper;
import com.hubindeveloper.train.member.req.PassengerSaveReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @description：乘客人接口类
 * @author：Kong
 * @date：2024/2/12
 */
@Service
public class PassengerService {
    @Resource
    private PassengerMapper passengerMapper;
    public void save(PassengerSaveReq req){
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setCreateTime(now);
        passenger.setCreateTime(now);
        passengerMapper.insert(passenger);
    }
}
