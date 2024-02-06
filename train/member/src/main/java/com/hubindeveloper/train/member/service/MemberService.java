package com.hubindeveloper.train.member.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.hubindeveloper.train.common.exception.BusinessException;
import com.hubindeveloper.train.common.exception.BusinessExceptionEnum;
import com.hubindeveloper.train.common.util.SnowUtil;
import com.hubindeveloper.train.member.domain.Member;
import com.hubindeveloper.train.member.domain.MemberExample;
import com.hubindeveloper.train.member.mapper.MemberMapper;
import com.hubindeveloper.train.member.req.MemberRegisterReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description：会员功能类
 * @author：Kong
 * @date：2024/2/6
 */
@Service
public class MemberService {
    @Resource
    private MemberMapper memberMapper;

    public int count(){
        return Math.toIntExact(memberMapper.countByExample(null));
    }

    public long register(MemberRegisterReq req){
        String mobile = req.getMobile();

        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = memberMapper.selectByExample(memberExample);

        if(CollUtil.isNotEmpty(members)){
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);

        memberMapper.insert(member);
        return member.getId();
    }
}
