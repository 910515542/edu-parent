package com.xiaoxuan.eduservice.client;

import com.xiaoxuan.utils.R;
import org.springframework.stereotype.Component;

@Component
public class VodHystrix implements VodClient {
    @Override
    public R removeVideo(String videoId) {
        return R.error().message("time out");
    }
}
