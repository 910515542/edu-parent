package com.xiaoxuan.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-order", fallback = OrderClientHystrix.class)
public interface OrderClient {
    @GetMapping("/order/isBuyCourse/{memberId}/{courseId}")
    Boolean isBuyCourse(@PathVariable("memberId") String memberId, @PathVariable("courseId") String courseId);
}
