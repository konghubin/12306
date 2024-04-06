package com.hubindeveloper.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hubindeveloper.train.business.domain.*;
import com.hubindeveloper.train.business.enums.SeatColEnum;
import com.hubindeveloper.train.common.resp.PageResp;
import com.hubindeveloper.train.common.util.SnowUtil;
import com.hubindeveloper.train.business.mapper.DailyTrainCarriageMapper;
import com.hubindeveloper.train.business.req.DailyTrainCarriageQueryReq;
import com.hubindeveloper.train.business.req.DailyTrainCarriageSaveReq;
import com.hubindeveloper.train.business.resp.DailyTrainCarriageQueryResp;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DailyTrainCarriageService {
    @Resource
    private DailyTrainCarriageMapper dailyTrainCarriageMapper;
    @Resource
    private TrainCarriageService trainCarriageService;

    public void save(DailyTrainCarriageSaveReq req){
        DateTime now = DateTime.now();

        List<SeatColEnum> seatColEnums = SeatColEnum.getColsByType(req.getSeatType());
        req.setColCount(seatColEnums.size());
        req.setSeatCount(req.getColCount() * req.getRowCount());

        DailyTrainCarriage dailyTrainCarriage = BeanUtil.copyProperties(req, DailyTrainCarriage.class);
        if(ObjectUtil.isNull(dailyTrainCarriage.getId())){
            dailyTrainCarriage.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainCarriage.setCreateTime(now);
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriageMapper.insert(dailyTrainCarriage);
        } else{
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriageMapper.updateByPrimaryKey(dailyTrainCarriage);
        }
    }

    public PageResp<DailyTrainCarriageQueryResp> queryList(DailyTrainCarriageQueryReq req){
        DailyTrainCarriageExample dailyTrainCarriageExample = new DailyTrainCarriageExample();
        dailyTrainCarriageExample.setOrderByClause("date desc, train_code asc, `index` asc");
        DailyTrainCarriageExample.Criteria criteria = dailyTrainCarriageExample.createCriteria();
        if(ObjectUtil.isNotNull(req.getDate())){
            criteria.andDateEqualTo(req.getDate());
        }
        if(ObjectUtil.isNotEmpty(req.getTrainCode())){
            criteria.andTrainCodeEqualTo(req.getTrainCode());
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainCarriage> dailyTrainCarriageList = dailyTrainCarriageMapper.selectByExample(dailyTrainCarriageExample);
        PageInfo<DailyTrainCarriage> pageInfo = new PageInfo<>(dailyTrainCarriageList);

        List<DailyTrainCarriageQueryResp> list = BeanUtil.copyToList(dailyTrainCarriageList, DailyTrainCarriageQueryResp.class);
        PageResp<DailyTrainCarriageQueryResp> pageResp = new PageResp<>();
        pageResp.setList(list);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id){
        dailyTrainCarriageMapper.deleteByPrimaryKey(id);
    }

    public void genDaily(Date date, String trainCode){
        // 删除某日某车次的车厢信息
        DailyTrainCarriageExample dailyTrainCarriageExample = new DailyTrainCarriageExample();
        DailyTrainCarriageExample.Criteria criteria = dailyTrainCarriageExample.createCriteria();
        criteria.andDateEqualTo(date).andTrainCodeEqualTo(trainCode);
        dailyTrainCarriageMapper.deleteByExample(dailyTrainCarriageExample);

        // 查出某车次的所有车厢信息
        List<TrainCarriage> trainCarriages = trainCarriageService.selectByTrainCode(trainCode);

        if(CollUtil.isEmpty(trainCarriages)){
            log.info("该车次没有车厢基础数据！");
            return;
        }
        // 生成某日某车次的车厢信息
        for(TrainCarriage trainCarriage : trainCarriages){
            DateTime now = DateTime.now();
            DailyTrainCarriage dailyTrainCarriage = BeanUtil.copyProperties(trainCarriage, DailyTrainCarriage.class);
            dailyTrainCarriage.setDate(date);
            dailyTrainCarriage.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainCarriage.setCreateTime(now);
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriageMapper.insert(dailyTrainCarriage);
        }
    }

    public List<DailyTrainCarriage> selectBySeatType (Date date, String trainCode, String seatType) {
        DailyTrainCarriageExample example = new DailyTrainCarriageExample();
        example.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode)
                .andSeatTypeEqualTo(seatType);
        return dailyTrainCarriageMapper.selectByExample(example);
    }
}
