package com.xiaoxuan.eduservice.client;

import com.xiaoxuan.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Component
@FeignClient(value = "service-ucenter")
public interface UcenterClient {
    //根据用户id获取用户信息
    // /ucenter/member/getMapInfo/{id}
    @GetMapping("/ucenter/member/getMapInfo/{id}")
    Map<String, String> getInfo(@PathVariable("id") String id);

    //根据token获取用户信息
    @GetMapping("/ucenter/member/getLoginInfo")
    R getUserInfo(HttpServletRequest request);
}
