package com.xiaoxuan.ucenter.service;

import com.xiaoxuan.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxuan.ucenter.vo.LoginVo;
import com.xiaoxuan.ucenter.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-04-06
 */
public interface MemberService extends IService<Member> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    Member getByOpenid(String openid);

    Integer countRegisterByDay(String day);
}
