package com.hubindeveloper.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hubindeveloper.train.business.domain.*;
import com.hubindeveloper.train.common.exception.BusinessException;
import com.hubindeveloper.train.common.exception.BusinessExceptionEnum;
import com.hubindeveloper.train.common.resp.PageResp;
import com.hubindeveloper.train.common.util.SnowUtil;
import com.hubindeveloper.train.business.mapper.TrainMapper;
import com.hubindeveloper.train.business.req.TrainQueryReq;
import com.hubindeveloper.train.business.req.TrainSaveReq;
import com.hubindeveloper.train.business.resp.TrainQueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainService {
    @Resource
    private TrainMapper trainMapper;
    public void save(TrainSaveReq req){
        DateTime now = DateTime.now();
        Train train = BeanUtil.copyProperties(req, Train.class);
        if(ObjectUtil.isNull(train.getId())){
            // 校验唯一键是否存在
            Train trainDB = selectByUnique(req.getCode());
            if(ObjectUtil.isNotEmpty(trainDB)){
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_CODE_UNIQUE_ERROR);
            }

            train.setId(SnowUtil.getSnowflakeNextId());
            train.setCreateTime(now);
            train.setUpdateTime(now);
            trainMapper.insert(train);
        } else{
            train.setUpdateTime(now);
            trainMapper.updateByPrimaryKey(train);
        }
    }

    private Train selectByUnique(String code) {
        TrainExample trainExample = new TrainExample();
        TrainExample.Criteria criteria = trainExample.createCriteria();
        criteria.andCodeEqualTo(code);
        List<Train> list = trainMapper.selectByExample(trainExample);
        if(CollUtil.isNotEmpty(list)){
            return list.get(0);
        }else{
            return null;
        }
    }

    public PageResp<TrainQueryResp> queryList(TrainQueryReq req){
        TrainExample trainExample = new TrainExample();
        trainExample.setOrderByClause("id desc");
        TrainExample.Criteria criteria = trainExample.createCriteria();

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Train> trainList = trainMapper.selectByExample(trainExample);
        PageInfo<Train> pageInfo = new PageInfo<>(trainList);

        List<TrainQueryResp> list = BeanUtil.copyToList(trainList, TrainQueryResp.class);
        PageResp<TrainQueryResp> pageResp = new PageResp<>();
        pageResp.setList(list);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public List<TrainQueryResp> queryAll(){
        List<Train> trainList = selectAll();

        return BeanUtil.copyToList(trainList, TrainQueryResp.class);
    }

    public List<Train> selectAll() {
        TrainExample trainExample = new TrainExample();
        trainExample.setOrderByClause("code asc");
        List<Train> trainList = trainMapper.selectByExample(trainExample);
        return trainList;
    }

    public void delete(Long id){
        trainMapper.deleteByPrimaryKey(id);
    }
}
