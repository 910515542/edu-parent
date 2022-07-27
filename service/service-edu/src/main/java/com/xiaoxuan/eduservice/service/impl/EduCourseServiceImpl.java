package com.xiaoxuan.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoxuan.eduservice.entity.EduChapter;
import com.xiaoxuan.eduservice.entity.EduCourse;
import com.xiaoxuan.eduservice.entity.EduCourseDescription;
import com.xiaoxuan.eduservice.entity.EduVideo;
import com.xiaoxuan.eduservice.mapper.EduCourseMapper;
import com.xiaoxuan.eduservice.service.EduChapterService;
import com.xiaoxuan.eduservice.service.EduCourseDescriptionService;
import com.xiaoxuan.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxuan.eduservice.service.EduVideoService;
import com.xiaoxuan.eduservice.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-03-22
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService descriptionService;
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduChapterService chapterService;

    @Override
    public String saveCourse(CourseInfoVo courseInfoVo) {
        EduCourse course = new EduCourse();
        EduCourseDescription description = new EduCourseDescription();
        //根据courseInfoVo设置课程的属性并保存到数据库
        BeanUtils.copyProperties(courseInfoVo, course);
        Boolean isSave1 = this.save(course);
        //设置description对象的属性并保存到数据库
        description.setDescription(courseInfoVo.getDescription());
        String courseId = course.getId();
        description.setId(courseId);//课程描述表的id是课程表的外键，使用课程表自动生成的id
        Boolean isSave2 = descriptionService.save(description);
        //成功返回的是课程ID，以便后续添加该课程的章节等其他信息
        if(isSave1 && isSave2) return courseId;
        else return "error";
    }

    @Override
    public CourseInfoVo getCourseInfoVo(String courseId) {
        EduCourse eduCourse = this.getById(courseId);
        EduCourseDescription courseDescription = descriptionService.getById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public String updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse course = new EduCourse();
        //将courseInfoVo对应属性赋值到EduCourse对象中
        BeanUtils.copyProperties(courseInfoVo, course);
        Boolean isUpdate1 = this.updateById(course);
        //将courseInfoVo对应属性赋值到EduCourseDescription对象中
        EduCourseDescription description = new EduCourseDescription();
        description.setDescription(courseInfoVo.getDescription());
        String courseId = courseInfoVo.getId();
        description.setId(courseId);//课程描述表的id是课程表的外键，使用课程表自动生成的id
        Boolean isUpdate2 = descriptionService.updateById(description);
        //成功返回的是课程ID，以便后续添加该课程的章节等其他信息
        if(isUpdate1 && isUpdate2) return courseId;
        else return "error";
    }

    @Override
    public CoursePublishInfo getCoursePublishInfo(String courseId) {
        return baseMapper.getCoursePublishInfoById(courseId);
    }

    @Override
    public Page<EduCourse> getPageConditionList(int current, int size, CourseQuery courseQuery) {
        //根据current和size获取page对象
        Page<EduCourse> page = new Page<>(current, size);
        //获取条件对象并设置条件
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();
        String courseName = courseQuery.getCourseName();
        String status = "";
        if(courseQuery.getIsPublish() != null){
            if(courseQuery.getIsPublish()) status += "Normal";
            else status += "Draft";
        }
        if(begin != null && !begin.equals("")){
            queryWrapper.ge("gmt_create", begin);
        }
        if(end != null && !end.equals("")){
            queryWrapper.le("gmt_create", end);
        }
        if(courseName != null && !courseName.equals("")){
            queryWrapper.like("title", courseName);
        }
        if(!status.equals(""))
        queryWrapper.eq("status", status);
        //用条件对象执行查询
        this.page(page, queryWrapper);
        //返回page对象中封装的数据列表
        return page;
    }

    @Override
    public boolean removeCourse(String courseId) {
        //删除该课程下的所有章节以及上传的视频
        QueryWrapper<EduChapter> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id", courseId);
        List<EduChapter> chapterList = chapterService.list(queryWrapper2);
        for(EduChapter chapter:chapterList){
            chapterService.removeChapter(chapter.getId());
        }
        //删除课程描述
        QueryWrapper<EduCourseDescription> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("id", courseId);
        descriptionService.remove(queryWrapper3);
        //最后删除本身课程信息
        return this.removeById(courseId);
    }

    @Override
    public Map<String, Object> FrontPageList(Page<EduCourse> pageParam, FrontCourseQueryVo courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        //一二级分类ID
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            queryWrapper.eq("subject_id", courseQuery.getSubjectId());
        }

        //根据前端传过来排序参数进行对某个字段排序
        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }
        //只显示已发布课程
        queryWrapper.eq("status", "Normal");
        //分页插件执行page
        this.page(pageParam, queryWrapper);
        //获取执行后page里的参数值
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public FrontCourseDetails selectInfoWebById(String id) {
        this.updatePageViewCount(id);
        return baseMapper.selectInfoWebById(id);
    }

    @Override
    public void updatePageViewCount(String id) {
        EduCourse course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }

    @Override
    public Page<EduCourse> searchCourse(int current, int size, String courseName) {
        //根据current和size获取page对象
        Page<EduCourse> page = new Page<>(current, size);
        //获取条件对象并设置条件
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if(courseName != null && !courseName.equals("")){
            queryWrapper.like("title", courseName);
        }
        //只显示已发布课程
        queryWrapper.eq("status", "Normal");
        //用条件对象执行查询
        this.page(page, queryWrapper);
        //返回page对象中封装的数据列表
        return page;
    }

    @Override
    public Map<String, Object> searchCourseByName(Page<EduCourse> pageParam, String courseName) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", courseName);
        //只显示已发布课程
        queryWrapper.eq("status", "Normal");
        //分页插件执行page
        this.page(pageParam, queryWrapper);
        //获取执行后page里的参数值
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public List<EduCourse> selectByTeacherId(String teacherId) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("teacher_id", teacherId);
        //按照最后更新时间倒序排列
        queryWrapper.orderByDesc("gmt_modified");
        //只显示已发布课程
        queryWrapper.eq("status", "Normal");
        List<EduCourse> courses = baseMapper.selectList(queryWrapper);
        return courses;
    }
}
