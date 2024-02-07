package com.hubindeveloper.train.member.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.hubindeveloper.train.common.exception.BusinessException;
import com.hubindeveloper.train.common.exception.BusinessExceptionEnum;
import com.hubindeveloper.train.common.util.SnowUtil;
import com.hubindeveloper.train.member.domain.Member;
import com.hubindeveloper.train.member.domain.MemberExample;
import com.hubindeveloper.train.member.mapper.MemberMapper;
import com.hubindeveloper.train.member.req.MemberLoginReq;
import com.hubindeveloper.train.member.req.MemberRegisterReq;
import com.hubindeveloper.train.member.req.MemberSendCodeReq;
import com.hubindeveloper.train.member.resp.MemberLoginResp;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description：会员功能类
 * @author：Kong
 * @date：2024/2/6
 */
@Service
@Slf4j
public class MemberService {
    @Resource
    private MemberMapper memberMapper;

    public int count(){
        return Math.toIntExact(memberMapper.countByExample(null));
    }

    public long register(MemberRegisterReq req){
        String mobile = req.getMobile();

        Member memberDB = selectMemberByMobile(mobile);

        if(ObjectUtil.isNotNull(memberDB)){
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);

        memberMapper.insert(member);
        return member.getId();
    }

    public void sendCode(MemberSendCodeReq req){
        String mobile = req.getMobile();

        Member memberDB = selectMemberByMobile(mobile);

        // 手机号不存在则插入一条记录
        if(ObjectUtil.isNull(memberDB)){
            log.info("手机号不存在，插入一条新记录！");
            Member member = new Member();
            member.setId(SnowUtil.getSnowflakeNextId());
            member.setMobile(mobile);

            memberMapper.insert(member);
        }else{
            log.info("手机号存在，不插入记录！");
        }

        String code = RandomUtil.randomString(4);
        log.info("生成短信验证码：{}", code);
    }

    public MemberLoginResp login(MemberLoginReq req){
        String mobile = req.getMobile();
        String code = req.getCode();

        Member memberDB = selectMemberByMobile(mobile);

        if(ObjectUtil.isNull(memberDB)){
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }

        // 校验短信验证码
        if(!"8888".equals(code)){
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_CODE_ERROR);
        }

        return new MemberLoginResp(memberDB.getId(), memberDB.getMobile());
    }

    private Member selectMemberByMobile(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = memberMapper.selectByExample(memberExample);

        if(CollUtil.isEmpty(members)){
            return null;
        }else{
            return members.get(0);
        }
    }
}
