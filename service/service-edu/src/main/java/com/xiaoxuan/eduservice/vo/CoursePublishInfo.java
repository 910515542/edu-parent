package com.xiaoxuan.eduservice.vo;

import lombok.Data;

@Data
public class CoursePublishInfo {
    private String id;
    private String title;
    private String price;
    private Integer lessonNum;
    private String cover;
    private String oneSubject;
    private String twoSubject;
    private String teacherName;
    private String description;
}
