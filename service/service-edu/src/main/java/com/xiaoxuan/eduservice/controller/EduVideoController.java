package com.xiaoxuan.eduservice.controller;


import com.xiaoxuan.eduservice.entity.EduVideo;
import com.xiaoxuan.eduservice.service.EduVideoService;
import com.xiaoxuan.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-03-22
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @ApiOperation("添加小节")
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean res = videoService.save(eduVideo);
        if(res) return R.ok();
        else return R.error();
    }
    @ApiOperation("删除小节")
    @DeleteMapping("/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId") String videoId){
        boolean res = videoService.removeVideoById(videoId);
        if(res) return R.ok();
        else return R.error();
    }
    @ApiOperation("获取小节")
    @GetMapping("/getVideo/{videoId}")
    public R getVideoById(@PathVariable("videoId") String videoId){
        EduVideo eduVideo = videoService.getById(videoId);
        return R.ok().data("video", eduVideo);
    }
    @ApiOperation("更新小节")
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        boolean res = videoService.updateById(eduVideo);
        if(res) return R.ok();
        else return R.error();
    }
}

