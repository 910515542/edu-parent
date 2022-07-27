package com.xiaoxuan.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoxuan.eduservice.entity.EduChapter;
import com.xiaoxuan.eduservice.entity.EduCourse;
import com.xiaoxuan.eduservice.service.EduCourseDescriptionService;
import com.xiaoxuan.eduservice.service.EduCourseService;
import com.xiaoxuan.eduservice.vo.CourseInfoVo;
import com.xiaoxuan.eduservice.vo.CoursePublishInfo;
import com.xiaoxuan.eduservice.vo.CourseQuery;
import com.xiaoxuan.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.QueryEval;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-03-22
 */
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;


    @ApiOperation("根据条件分页获取课程")
    @PostMapping("/pageListCondition/{current}/{size}")
    public R listByPageAndCondition(@PathVariable("current") int current,
                                    @PathVariable("size") int size,
                                    @RequestBody CourseQuery courseQuery){
        Page<EduCourse> page = courseService.getPageConditionList(current, size, courseQuery);
        return R.ok().data("list", page.getRecords()).data("total", page.getTotal());
    }

    @ApiOperation("添加课程基本信息")
    @PostMapping("/addCourseInfo")
    public R addCourseSimpleInfo(@RequestBody CourseInfoVo courseInfoVo){
        //成功返回的是课程ID，以便后续添加该课程的章节等其他信息
        String res = courseService.saveCourse(courseInfoVo);
        if(!"error".equals(res)  && !"".equals(res) )
            return R.ok().data("courseId", res);
        else
            return R.error();
    }
    @ApiOperation("根据课程id获取课程基本信息")
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfoById(@PathVariable("courseId") String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfoVo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }
    @ApiOperation("修改课程信息")
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String res = courseService.updateCourseInfo(courseInfoVo);
        if(!"error".equals(res) && !"".equals(res)){
            return R.ok().data("courseId", res);
        }else{
            return R.error();
        }
    }
    @ApiOperation("获取课程发布需要确认的课程信息")
    @GetMapping("/coursePublishInfo/{courseId}")
    public R getCoursePublishInfo(@PathVariable("courseId") String courseId){
        CoursePublishInfo coursePublishInfo = courseService.getCoursePublishInfo(courseId);
        return R.ok().data("coursePublishInfo", coursePublishInfo);
    }
    @ApiOperation("发布课程")
    @PostMapping("/publish/{courseId}")
    public R publishCourse(@PathVariable("courseId") String courseId){
        EduCourse course = new EduCourse();
        course.setId(courseId);
        course.setStatus("Normal");
        boolean res = courseService.updateById(course);
        if(res) return R.ok();
        else return R.error();
    }
    @ApiOperation("下架课程")
    @PostMapping("/down/{courseId}")
    public R pullCourse(@PathVariable("courseId") String courseId){
        EduCourse course = new EduCourse();
        course.setId(courseId);
        course.setStatus("Draft");
        boolean res = courseService.updateById(course);
        if(res) return R.ok();
        else return R.error();
    }
    @ApiOperation("删除课程及与该课程相关的所有章节、小节和视频")
    @DeleteMapping("/delete/{courseId}")
    public R deleteCourse(@PathVariable("courseId") String courseId){
        boolean res = courseService.removeCourse(courseId);
        if(res) return R.ok();
        else return R.error();


    }

}

