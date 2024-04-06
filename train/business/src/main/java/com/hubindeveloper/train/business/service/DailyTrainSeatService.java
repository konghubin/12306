package com.hubindeveloper.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hubindeveloper.train.business.domain.*;
import com.hubindeveloper.train.common.resp.PageResp;
import com.hubindeveloper.train.common.util.SnowUtil;
import com.hubindeveloper.train.business.mapper.DailyTrainSeatMapper;
import com.hubindeveloper.train.business.req.DailyTrainSeatQueryReq;
import com.hubindeveloper.train.business.req.DailyTrainSeatSaveReq;
import com.hubindeveloper.train.business.resp.DailyTrainSeatQueryResp;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DailyTrainSeatService {
    @Resource
    private DailyTrainSeatMapper dailyTrainSeatMapper;
    @Resource
    private TrainSeatService trainSeatService;
    @Resource
    private TrainStationService trainStationService;

    
    public void save(DailyTrainSeatSaveReq req){
        DateTime now = DateTime.now();
        DailyTrainSeat dailyTrainSeat = BeanUtil.copyProperties(req, DailyTrainSeat.class);
        if(ObjectUtil.isNull(dailyTrainSeat.getId())){
            dailyTrainSeat.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeatMapper.insert(dailyTrainSeat);
        } else{
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeatMapper.updateByPrimaryKey(dailyTrainSeat);
        }
    }

    public PageResp<DailyTrainSeatQueryResp> queryList(DailyTrainSeatQueryReq req){
        DailyTrainSeatExample dailyTrainSeatExample = new DailyTrainSeatExample();
        dailyTrainSeatExample.setOrderByClause("date asc, train_code asc, carriage_index asc, carriage_seat_index asc");
        DailyTrainSeatExample.Criteria criteria = dailyTrainSeatExample.createCriteria();

        if(ObjectUtil.isNotEmpty(req.getTrainCode())){
            criteria.andTrainCodeEqualTo(req.getTrainCode());
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainSeat> dailyTrainSeatList = dailyTrainSeatMapper.selectByExample(dailyTrainSeatExample);
        PageInfo<DailyTrainSeat> pageInfo = new PageInfo<>(dailyTrainSeatList);

        List<DailyTrainSeatQueryResp> list = BeanUtil.copyToList(dailyTrainSeatList, DailyTrainSeatQueryResp.class);
        PageResp<DailyTrainSeatQueryResp> pageResp = new PageResp<>();
        pageResp.setList(list);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id){
        dailyTrainSeatMapper.deleteByPrimaryKey(id);
    }

    public void genDaily(Date date, String trainCode) {
        // 删除某日某车次的座位信息
        DailyTrainSeatExample dailyTrainSeatExample = new DailyTrainSeatExample();
        DailyTrainSeatExample.Criteria criteria = dailyTrainSeatExample.createCriteria();
        criteria.andDateEqualTo(date).andTrainCodeEqualTo(trainCode);
        dailyTrainSeatMapper.deleteByExample(dailyTrainSeatExample);

        List<TrainStation> trainStations = trainStationService.selectByTrainCode(trainCode);
        String sell = StrUtil.fillBefore("", '0', trainStations.size() - 1);

        // 查出某车次的所有座位信息
        List<TrainSeat> trainSeats = trainSeatService.selectByTrainCode(trainCode);

        if (CollUtil.isEmpty(trainSeats)) {
            log.info("该车次没有座位基础数据！");
            return;
        }
        // 生成某日某车次的座位信息
        for (TrainSeat trainSeat : trainSeats) {
            DateTime now = DateTime.now();
            DailyTrainSeat dailyTrainSeat = BeanUtil.copyProperties(trainSeat, DailyTrainSeat.class);
            dailyTrainSeat.setDate(date);
            dailyTrainSeat.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeat.setSell(sell);
            dailyTrainSeatMapper.insert(dailyTrainSeat);
        }
    }

    public int countSeat(Date date, String trainCode, String seatType){
        DailyTrainSeatExample dailyTrainSeatExample = new DailyTrainSeatExample();
        dailyTrainSeatExample.createCriteria().andDateEqualTo(date).andTrainCodeEqualTo(trainCode).andSeatTypeEqualTo(seatType);
        long count = dailyTrainSeatMapper.countByExample(dailyTrainSeatExample);
        if(count == 0L){
            return -1;
        }
        return (int)count;
    }

    public List<DailyTrainSeat> selectByCarriage(Date date, String trainCode, Integer carriageIndex) {
        DailyTrainSeatExample example = new DailyTrainSeatExample();
        example.setOrderByClause("carriage_seat_index asc");
        example.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode)
                .andCarriageIndexEqualTo(carriageIndex);
        return dailyTrainSeatMapper.selectByExample(example);
    }
}
