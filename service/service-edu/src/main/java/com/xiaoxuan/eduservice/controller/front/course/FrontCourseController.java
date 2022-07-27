package com.xiaoxuan.eduservice.controller.front.course;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoxuan.eduservice.client.OrderClient;
import com.xiaoxuan.eduservice.entity.EduCourse;
import com.xiaoxuan.eduservice.entity.chapter.ChapterVo;
import com.xiaoxuan.eduservice.service.EduChapterService;
import com.xiaoxuan.eduservice.service.EduCourseService;
import com.xiaoxuan.eduservice.vo.FrontCourseDetails;
import com.xiaoxuan.eduservice.vo.FrontCourseQueryVo;
import com.xiaoxuan.servicebase.exceptionhandler.GuliException;
import com.xiaoxuan.utils.JwtUtils;
import com.xiaoxuan.utils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/eduservice/frontCourse")
@RestController
public class FrontCourseController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private OrderClient orderClient;

    @ApiOperation("分页课程列表")
    @PostMapping("list/{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) FrontCourseQueryVo courseQuery){
        Page<EduCourse> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.FrontPageList(pageParam, courseQuery);
        return  R.ok().data(map);
    }

    @ApiOperation("通过课程名查询课程")
    @PostMapping("searchCourseByName/{page}/{limit}/{courseName}")
    public R searchCourseByName(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseName", value = "课程名", required = false)
            @PathVariable String courseName){
        Page<EduCourse> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.searchCourseByName(pageParam, courseName);
        return  R.ok().data(map);
    }

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation(value = "根据ID查询前台课程详情页包括的信息")
    @GetMapping("/{courseId}")
    public R getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId, HttpServletRequest request){

        //该课程的浏览量+1
        courseService.updatePageViewCount(courseId);

        //查询课程信息和讲师信息,自己写连表查询SQL语句
        FrontCourseDetails courseWebVo = courseService.selectInfoWebById(courseId);

        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.getChapter(courseId);

        //查找是否存在当前用户与当前课程的订单
        System.out.println(JwtUtils.getMemberIdByJwtToken(request));
        Boolean isBuy = orderClient.isBuyCourse(JwtUtils.getMemberIdByJwtToken(request), courseId);
        System.out.println("isBuy" + isBuy);
//        if(isBuy == null){
//            return R.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList).data("isBuy", "查询购买失败");
//        }

        return R.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList).data("isBuy", isBuy);
    }
    @ApiOperation("远程调用：根据课程id获取课程基本信息，Map封装")
    @GetMapping("/getCourseMapInfo/{courseId}")
    public Map<String, String> getCourseMapInfoById(@PathVariable("courseId") String courseId){
        //查询课程信息和讲师信息
        FrontCourseDetails course = courseService.selectInfoWebById(courseId);
        Map<String, String> map = new HashMap<>();
        map.put("courseTitle", course.getTitle());
        map.put("courseCover", course.getCover());
        map.put("coursePrice", course.getPrice().toString());
        map.put("courseTeacher", course.getTeacherName());
        return map;
    }
}
