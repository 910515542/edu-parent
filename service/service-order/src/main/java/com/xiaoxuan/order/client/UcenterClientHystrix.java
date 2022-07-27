package com.xiaoxuan.order.client;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UcenterClientHystrix implements UcenterClient {
    @Override
    public Map<String, String> getUserInfo(String memberId) {
        return null;
    }
}
