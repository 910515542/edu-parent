package com.xiaoxuan.order.client;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ServiceEduClientHystrix implements ServiceEduClient {
    @Override
    public Map<String, String> getCourseInfoDto(String courseId) {
        return null;
    }
}
