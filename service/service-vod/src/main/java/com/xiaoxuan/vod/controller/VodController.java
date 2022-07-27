package com.xiaoxuan.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.xiaoxuan.servicebase.exceptionhandler.GuliException;
import com.xiaoxuan.utils.R;
import com.xiaoxuan.vod.service.VodService;
import com.xiaoxuan.vod.utils.ConstantPropertiesUtil;
import com.xiaoxuan.vod.utils.InitVod;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/vod/video")
@RestController
public class VodController {
    @Autowired
    private VodService vodService;

    @ApiOperation("上传视频")
    @PostMapping("/upload")
    public R uploadVideo(@RequestBody MultipartFile file){
        String videoId = vodService.uploadVideoByStream(file);
        return R.ok().data("videoId", videoId);
    }

    @ApiOperation("删除视频")
    @DeleteMapping("/delete/{videoId}")
    public R uploadVideo(@PathVariable("videoId") String videoId){
        vodService.deleteVideoById(videoId);
        return R.ok();
    }

    @ApiOperation("获取视频播放凭证")
    @GetMapping("/getPlayAuth/{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId){

        try {
            //获取阿里云存储相关常量
            String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;

            //初始化
            DefaultAcsClient client = InitVod.initVodClient(accessKeyId, accessKeySecret);

            //请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);

            //响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            //得到播放凭证
            String playAuth = response.getPlayAuth();
            //返回结果
            return R.ok().message("获取凭证成功").data("playAuth", playAuth);
        }catch(Exception e){
            e.printStackTrace();
            throw new GuliException(20001, "获取凭证失败！");
        }
    }

}
