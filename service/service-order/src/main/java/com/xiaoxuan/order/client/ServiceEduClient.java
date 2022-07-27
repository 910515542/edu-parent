package com.xiaoxuan.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Component
@FeignClient(value = "service-edu", fallback = ServiceEduClientHystrix.class)
public interface ServiceEduClient {
    //根据课程id查询课程信息
    @GetMapping("/eduservice/frontCourse/getCourseMapInfo/{courseId}")
    Map<String, String> getCourseInfoDto(@PathVariable("courseId") String courseId);
}
