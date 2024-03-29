package com.hubindeveloper.train.${module}.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hubindeveloper.train.common.resp.PageResp;
import com.hubindeveloper.train.common.util.SnowUtil;
import com.hubindeveloper.train.${module}.domain.${Domain};
import com.hubindeveloper.train.${module}.domain.${Domain}Example;
import com.hubindeveloper.train.${module}.mapper.${Domain}Mapper;
import com.hubindeveloper.train.${module}.req.${Domain}QueryReq;
import com.hubindeveloper.train.${module}.req.${Domain}SaveReq;
import com.hubindeveloper.train.${module}.resp.${Domain}QueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ${Domain}Service {
    @Resource
    private ${Domain}Mapper ${domain}Mapper;
    public void save(${Domain}SaveReq req){
        DateTime now = DateTime.now();
        ${Domain} ${domain} = BeanUtil.copyProperties(req, ${Domain}.class);
        if(ObjectUtil.isNull(${domain}.getId())){
            ${domain}.setId(SnowUtil.getSnowflakeNextId());
            ${domain}.setCreateTime(now);
            ${domain}.setUpdateTime(now);
            ${domain}Mapper.insert(${domain});
        } else{
            ${domain}.setUpdateTime(now);
            ${domain}Mapper.updateByPrimaryKey(${domain});
        }
    }

    public PageResp<${Domain}QueryResp> queryList(${Domain}QueryReq req){
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        ${domain}Example.setOrderByClause("id desc");
        ${Domain}Example.Criteria criteria = ${domain}Example.createCriteria();

        PageHelper.startPage(req.getPage(), req.getSize());
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(${domain}Example);
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}List);

        List<${Domain}QueryResp> list = BeanUtil.copyToList(${domain}List, ${Domain}QueryResp.class);
        PageResp<${Domain}QueryResp> pageResp = new PageResp<>();
        pageResp.setList(list);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id){
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}
