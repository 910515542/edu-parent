package com.xiaoxuan.eduservice.client;

import org.springframework.stereotype.Component;

@Component
public class OrderClientHystrix implements  OrderClient{
    @Override
    public Boolean isBuyCourse(String memberId, String courseId) {
        return null;
    }
}
