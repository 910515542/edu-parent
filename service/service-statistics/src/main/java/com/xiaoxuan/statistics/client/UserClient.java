package com.xiaoxuan.statistics.client;

import com.xiaoxuan.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-ucenter", fallback = UserClientHystrix.class)
public interface UserClient {
    @GetMapping("/ucenter/member/countregister/{day}")
    R registerCount(@PathVariable("day") String day);
}
