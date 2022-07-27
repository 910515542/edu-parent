package com.xiaoxuan.eduservice.vo;

import lombok.Data;

@Data
public class CourseQuery {
    String courseName;
    Boolean isPublish;
    String begin;
    String end;
}
