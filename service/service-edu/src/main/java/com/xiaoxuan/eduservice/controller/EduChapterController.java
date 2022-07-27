package com.xiaoxuan.eduservice.controller;


import com.xiaoxuan.eduservice.entity.EduChapter;
import com.xiaoxuan.eduservice.entity.chapter.ChapterVo;
import com.xiaoxuan.eduservice.service.EduChapterService;
import com.xiaoxuan.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-03-22
 */
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation("得到课程所有章节及小节")
    @GetMapping("/getChapterAndVideo/{courseId}")
    public R getChapterAndVideo(@PathVariable("courseId") String courseId){
        List<ChapterVo> chapterList = chapterService.getChapter(courseId);
        return R.ok().data("chapterList", chapterList);
    }
    @ApiOperation("添加章节")
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter chapter){
        boolean res = chapterService.save(chapter);
        if(res) return R.ok();
        else return R.error();
    }

    @ApiOperation("通过id查询章节")
    @GetMapping("/getChapter/{chapterId}")
    public R getChapterById(@PathVariable("chapterId") String chapterId){
        EduChapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter", chapter);
    }

    @ApiOperation("修改章节")
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter chapter){
        boolean res = chapterService.updateById(chapter);
        if(res) return R.ok();
        else return R.error();
    }

    @ApiOperation("删除章节")
    @DeleteMapping("/deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable("chapterId") String chapterId){
        boolean res = chapterService.removeChapter(chapterId);
        if(res) return R.ok();
        else return R.error();
    }

}

