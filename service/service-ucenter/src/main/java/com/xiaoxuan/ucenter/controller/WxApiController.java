package com.xiaoxuan.ucenter.controller;

import com.google.gson.Gson;
import com.xiaoxuan.servicebase.exceptionhandler.GuliException;
import com.xiaoxuan.ucenter.entity.Member;
import com.xiaoxuan.ucenter.service.MemberService;
import com.xiaoxuan.ucenter.util.ConstantPropertiesUtil;
import com.xiaoxuan.ucenter.util.HttpClientUtils;
import com.xiaoxuan.utils.JwtUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private MemberService memberService;

    @GetMapping("login")
    public String genQrConnect(HttpSession session) {

        // 微信开放平台授权baseUrl,使用String.format(...)形式拼接更直观方便
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
//        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        String redirectUrl;
        try {
            redirectUrl = URLEncoder.encode("http://guli.shop/api/ucenter/wx/callback", "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }

        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "test";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        System.out.println("state = " + state);

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                "wxed9954c01bb89b47",
                redirectUrl,
                state);

        return "redirect:" + qrcodeUrl;
    }

    @ApiOperation("授权成功后微信端自动跳转到该controller")
    @GetMapping("/callback")
    public String callback(String code, String state){

        //得到授权临时票据code
        System.out.println(code);
        System.out.println(state);

        //1、从redis中将state获取出来，和当前传入的state作比较
        //如果一致则放行，如果不一致则抛出异常：非法访问

        //2、向认证方（微信）发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);

        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessToken=============" + result);
        } catch (Exception e) {
            throw new GuliException(20001, "获取access_token失败");
        }

        //解析认证方（微信）发过来的json字符串为map对象并获取对应键值
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        String accessToken = (String)map.get("access_token");
        String openid = (String)map.get("openid");

        //根据认证方发来的openId查询数据库当前用用户是否存在，存在的话就直接重定向到主页
        //否则需要从认证方根据前面获取的的token去获取需要的信息加入自己的数据库
        Member member = memberService.getByOpenid(openid);
        if(member == null){
            System.out.println("新用户注册");

            //3、从认证方根据前面获取的的token去获取需要的信息加入自己的数据库
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultUserInfo==========" + resultUserInfo);
            } catch (Exception e) {
                throw new GuliException(20001, "获取用户信息失败");
            }

            //解析json
            HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String)mapUserInfo.get("nickname");
            String headimgurl = (String)mapUserInfo.get("headimgurl");

            //将从认证方获取的信息加入自己的数据库作为用户的信息
            member = new Member();
            member.setNickname(nickname);
            member.setOpenid(openid);
            member.setAvatar(headimgurl);
            memberService.save(member);
        }

        // 生成jwt
        String token = JwtUtils.getJwtToken(member.getId(),member.getNickname());

        //存入cookie
        //CookieUtils.setCookie(request, response, "guli_jwt_token", token);

        //因为端口号不同存在蛞蝓问题，cookie不能跨域，所以这里使用url重写
        return "redirect:http://localhost:3000?token=" + token;
    }
}
