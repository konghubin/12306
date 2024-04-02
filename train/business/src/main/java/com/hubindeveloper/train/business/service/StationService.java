package com.hubindeveloper.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hubindeveloper.train.common.exception.BusinessException;
import com.hubindeveloper.train.common.exception.BusinessExceptionEnum;
import com.hubindeveloper.train.common.resp.PageResp;
import com.hubindeveloper.train.common.util.SnowUtil;
import com.hubindeveloper.train.business.domain.Station;
import com.hubindeveloper.train.business.domain.StationExample;
import com.hubindeveloper.train.business.mapper.StationMapper;
import com.hubindeveloper.train.business.req.StationQueryReq;
import com.hubindeveloper.train.business.req.StationSaveReq;
import com.hubindeveloper.train.business.resp.StationQueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {
    @Resource
    private StationMapper stationMapper;
    public void save(StationSaveReq req){
        DateTime now = DateTime.now();
        Station station = BeanUtil.copyProperties(req, Station.class);
        if(ObjectUtil.isNull(station.getId())){
            // 校验唯一键是否存在
            Station stationDB = selectByUnique(req.getName());
            if(ObjectUtil.isNotEmpty(stationDB)){
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_STATION_NAME_UNIQUE_ERROR);
            }

            station.setId(SnowUtil.getSnowflakeNextId());
            station.setCreateTime(now);
            station.setUpdateTime(now);
            stationMapper.insert(station);
        } else{
            station.setUpdateTime(now);
            stationMapper.updateByPrimaryKey(station);
        }
    }

    private Station selectByUnique(String name) {
        StationExample stationExample = new StationExample();
        StationExample.Criteria criteria = stationExample.createCriteria();
        criteria.andNameEqualTo(name);
        List<Station> list = stationMapper.selectByExample(stationExample);
        if(CollUtil.isNotEmpty(list)){
            return list.get(0);
        }else{
            return null;
        }
    }

    public PageResp<StationQueryResp> queryList(StationQueryReq req){
        StationExample stationExample = new StationExample();
        stationExample.setOrderByClause("id desc");
        StationExample.Criteria criteria = stationExample.createCriteria();

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Station> stationList = stationMapper.selectByExample(stationExample);
        PageInfo<Station> pageInfo = new PageInfo<>(stationList);

        List<StationQueryResp> list = BeanUtil.copyToList(stationList, StationQueryResp.class);
        PageResp<StationQueryResp> pageResp = new PageResp<>();
        pageResp.setList(list);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public List<StationQueryResp> queryAll(){
        StationExample stationExample = new StationExample();
        stationExample.setOrderByClause("name_pinyin asc");
        List<Station> stationList = stationMapper.selectByExample(stationExample);

        return BeanUtil.copyToList(stationList, StationQueryResp.class);
    }

    public void delete(Long id){
        stationMapper.deleteByPrimaryKey(id);
    }
}
