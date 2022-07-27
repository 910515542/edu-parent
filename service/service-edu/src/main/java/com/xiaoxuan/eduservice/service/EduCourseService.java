package com.xiaoxuan.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoxuan.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxuan.eduservice.vo.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-03-22
 */
public interface EduCourseService extends IService<EduCourse> {

    public String saveCourse(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfoVo(String courseId);

    String updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishInfo getCoursePublishInfo(String courseId);

    Page<EduCourse> getPageConditionList(int current, int size, CourseQuery courseQuery);

    boolean removeCourse(String courseId);

    Map<String, Object> FrontPageList(Page<EduCourse> pageParam, FrontCourseQueryVo courseQuery);

    FrontCourseDetails selectInfoWebById(String id);

    void updatePageViewCount(String id);

    Page<EduCourse> searchCourse(int current, int size, String courseName);

    Map<String, Object> searchCourseByName(Page<EduCourse> pageParam, String courseName);

    List<EduCourse> selectByTeacherId(String id);
}
