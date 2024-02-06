package com.hubindeveloper.train.member.service;

import cn.hutool.core.collection.CollUtil;
import com.hubindeveloper.train.member.domain.Member;
import com.hubindeveloper.train.member.domain.MemberExample;
import com.hubindeveloper.train.member.mapper.MemberMapper;
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

    public long register(String mobile){
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = memberMapper.selectByExample(memberExample);

        if(CollUtil.isNotEmpty(members)){
            throw new RuntimeException("手机号已注册！");
        }

        Member member = new Member();
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);

        memberMapper.insert(member);
        return member.getId();
    }
}
