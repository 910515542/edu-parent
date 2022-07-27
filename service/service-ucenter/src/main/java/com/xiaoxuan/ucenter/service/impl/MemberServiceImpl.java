package com.xiaoxuan.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxuan.servicebase.exceptionhandler.GuliException;
import com.xiaoxuan.ucenter.entity.Member;
import com.xiaoxuan.ucenter.mapper.MemberMapper;
import com.xiaoxuan.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxuan.ucenter.vo.LoginVo;
import com.xiaoxuan.ucenter.vo.RegisterVo;
import com.xiaoxuan.utils.JwtUtils;
import com.xiaoxuan.utils.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-04-06
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();


        //校验参数
        if(StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)) {
            throw new GuliException(20001,"登录信息未填写完全");
        }

        //获取会员
        Member member = baseMapper.selectOne(new QueryWrapper<Member>().eq("mobile", mobile));
        if(null == member) {
            throw new GuliException(20001,"账号有误");
        }

        //MD5校验密码,这种加密方式只能加密不能解密
        if(!MD5.encrypt(password).equals(member.getPassword())) {
            throw new GuliException(20001,"密码错误");
        }

        //校验是否被禁用
        if(member.getIsDisabled()) {
            throw new GuliException(20001,"该账户已被禁用");
        }

        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new GuliException(20001,"信息未填写完全");
        }

        //校验校验验证码
        //从redis获取发送的验证码
//        String mobleCode = redisTemplate.opsForValue().get(mobile);
        String mobleCode = "666666";
        if(!code.equals(mobleCode)) {
            throw new GuliException(20001,"验证码错误");
        }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<Member>().eq("mobile", mobile));
        if(count.intValue() > 0) {
            throw new GuliException(20001,"手机号已被注册");
        }

        //添加注册信息到数据库
        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(password));//MD5加密后存储在数据库
        member.setIsDisabled(false);//是否禁用,默认不禁用
        //默认头像
        member.setAvatar("http://online-edu-0001.oss-cn-beijing.aliyuncs.com/2021/04/03/fef10a03abd34bd89ba8805ce3909e15file.png");
        this.save(member);
    }

    @Override
    public Member getByOpenid(String openid) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        return this.getOne(queryWrapper);
    }

    @Override
    public Integer countRegisterByDay(String day) {
        return baseMapper.selectRegisterCount(day);
    }
}
