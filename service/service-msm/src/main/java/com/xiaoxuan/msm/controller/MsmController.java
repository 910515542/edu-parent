package com.xiaoxuan.msm.controller;


import com.xiaoxuan.msm.service.MsmService;
import com.xiaoxuan.msm.utils.RandomUtil;
import com.xiaoxuan.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/msm/member")
public class MsmController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation("使用阿里云短信服务发送短信")
    @GetMapping("/send/{number}")
    public R sendMessage(@PathVariable("number") String number){
        /*
        //如果Redis缓存中还存在该手机号码的随机数直接返回
        String code = redisTemplate.opsForValue().get(number);
        if(!StringUtils.isEmpty(code)) return R.ok();
        //1、生成随机值
        String sixBitRandom = RandomUtil.getSixBitRandom();
        //将随机值封装成Map
        Map<String, Object> map = new HashMap<>();
        map.put("code", sixBitRandom);
        //2、交给阿里云发送用短信发送随即值
        boolean isSend = msmService.send(map, number);
        if(isSend){
            //发送成功就将电话号码和随机数存到Redis中，并设置过期时间
            redisTemplate.opsForValue().set(number, sixBitRandom,5, TimeUnit.MINUTES);
            return R.ok();
        }else{
            return R.error().message("短信发送失败");
        }*/
        return R.ok();
    }
}
