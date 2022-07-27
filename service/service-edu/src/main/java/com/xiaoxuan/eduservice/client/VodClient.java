package com.xiaoxuan.eduservice.client;

import com.xiaoxuan.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-vod", fallback = VodHystrix.class)
@Component
public interface VodClient {
    @DeleteMapping(value = "/vod/video/delete/{videoId}")
    R removeVideo(@PathVariable("videoId") String videoId);
}
