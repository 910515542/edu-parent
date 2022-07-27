package com.xiaoxuan.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxuan.eduservice.entity.EduCourse;
import com.xiaoxuan.eduservice.entity.EduTeacher;
import com.xiaoxuan.eduservice.service.EduCourseService;
import com.xiaoxuan.eduservice.service.EduTeacherService;
import com.xiaoxuan.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/front")
public class IndexController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    //查询前8条热门课程，查询前4条名师
    @Cacheable(key = "'courseAndTeacher'", value = "indexData")
    @ApiOperation("用户端首页查询热门课程、名师")
    @GetMapping("/index")
    public R index() {
        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count");
        wrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(wrapper);
        System.out.println("---------courseList--------");
        System.out.println(courseList);

        //查询前4条名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByAsc("sort");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);

        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
