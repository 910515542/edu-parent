package com.xiaoxuan.ucenter.controller;


import com.xiaoxuan.servicebase.exceptionhandler.GuliException;
import com.xiaoxuan.ucenter.entity.Member;
import com.xiaoxuan.ucenter.service.MemberService;
import com.xiaoxuan.ucenter.vo.LoginVo;
import com.xiaoxuan.ucenter.vo.RegisterVo;
import com.xiaoxuan.utils.JwtUtils;
import com.xiaoxuan.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-04-06
 */
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "会员登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo) {
        //返回使用JWT生成的token
        String token = memberService.login(loginVo);
        return R.ok().data("token", token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation("根据token获取用户")
    @GetMapping("/getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
        //调用JWTUtils得到对应token的用户信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getById(memberId);
        System.out.println("4执行了查询语句");
        return R.ok().data("member", member);

    }

    @ApiOperation("远程调用：根据id获取用户")
    @GetMapping("/getMapInfo/{id}")
    public Map<String, String> getInfo(@PathVariable String id) {
        //根据用户id获取用户信息
        System.out.println("执行了用户中心服务调用！");
        Member member = memberService.getById(id);
        Map<String, String> map = new HashMap<>();
        map.put("avatar", member.getAvatar());
        map.put("nickname", member.getNickname());
        map.put("userMobile", member.getMobile());
        System.out.println("根据查询用户被调用！");
        return map;
    }
    @ApiOperation("远程调用：统计用户某天的注册人数")
    @GetMapping(value = "/countregister/{day}")
    public R registerCount(
            @PathVariable String day){
        System.out.println("统计注册人数被调用");
        Integer count = memberService.countRegisterByDay(day);
        return R.ok().data("countRegister", count);
    }

}

