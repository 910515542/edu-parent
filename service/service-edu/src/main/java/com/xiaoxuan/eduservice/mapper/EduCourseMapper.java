package com.xiaoxuan.eduservice.mapper;

import com.xiaoxuan.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoxuan.eduservice.vo.CoursePublishInfo;
import com.xiaoxuan.eduservice.vo.FrontCourseDetails;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-03-22
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishInfo getCoursePublishInfoById(@Param("id") String courseId);

    FrontCourseDetails selectInfoWebById(@Param("id") String courseId);
}
