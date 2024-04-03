package com.hubindeveloper.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hubindeveloper.train.common.resp.PageResp;
import com.hubindeveloper.train.common.util.SnowUtil;
import com.hubindeveloper.train.business.domain.DailyTrainStation;
import com.hubindeveloper.train.business.domain.DailyTrainStationExample;
import com.hubindeveloper.train.business.mapper.DailyTrainStationMapper;
import com.hubindeveloper.train.business.req.DailyTrainStationQueryReq;
import com.hubindeveloper.train.business.req.DailyTrainStationSaveReq;
import com.hubindeveloper.train.business.resp.DailyTrainStationQueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyTrainStationService {
    @Resource
    private DailyTrainStationMapper dailyTrainStationMapper;
    public void save(DailyTrainStationSaveReq req){
        DateTime now = DateTime.now();
        DailyTrainStation dailyTrainStation = BeanUtil.copyProperties(req, DailyTrainStation.class);
        if(ObjectUtil.isNull(dailyTrainStation.getId())){
            dailyTrainStation.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainStation.setCreateTime(now);
            dailyTrainStation.setUpdateTime(now);
            dailyTrainStationMapper.insert(dailyTrainStation);
        } else{
            dailyTrainStation.setUpdateTime(now);
            dailyTrainStationMapper.updateByPrimaryKey(dailyTrainStation);
        }
    }

    public PageResp<DailyTrainStationQueryResp> queryList(DailyTrainStationQueryReq req){
        DailyTrainStationExample dailyTrainStationExample = new DailyTrainStationExample();
        dailyTrainStationExample.setOrderByClause("id desc");
        DailyTrainStationExample.Criteria criteria = dailyTrainStationExample.createCriteria();

        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainStation> dailyTrainStationList = dailyTrainStationMapper.selectByExample(dailyTrainStationExample);
        PageInfo<DailyTrainStation> pageInfo = new PageInfo<>(dailyTrainStationList);

        List<DailyTrainStationQueryResp> list = BeanUtil.copyToList(dailyTrainStationList, DailyTrainStationQueryResp.class);
        PageResp<DailyTrainStationQueryResp> pageResp = new PageResp<>();
        pageResp.setList(list);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id){
        dailyTrainStationMapper.deleteByPrimaryKey(id);
    }
}
