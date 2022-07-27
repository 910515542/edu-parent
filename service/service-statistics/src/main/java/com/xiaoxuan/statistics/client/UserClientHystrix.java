package com.xiaoxuan.statistics.client;

import com.xiaoxuan.utils.R;
import org.springframework.stereotype.Component;

@Component
public class UserClientHystrix implements UserClient {
    @Override
    public R registerCount(String day) {
        return null;
    }
}
