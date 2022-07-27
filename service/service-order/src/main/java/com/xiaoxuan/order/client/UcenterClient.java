package com.xiaoxuan.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Component
@FeignClient(name = "service-ucenter", fallback = UcenterClientHystrix.class)
public interface UcenterClient {
    //根据用户id获取用户信息
    @GetMapping("/ucenter/member/getMapInfo/{id}")
    Map<String, String> getUserInfo(@PathVariable("id") String memberId);
}
