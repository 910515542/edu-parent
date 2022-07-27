package com.xiaoxuan.vod.service.imp;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.xiaoxuan.servicebase.exceptionhandler.GuliException;
import com.xiaoxuan.vod.service.VodService;
import com.xiaoxuan.vod.utils.ConstantPropertiesUtil;
import com.xiaoxuan.vod.utils.InitVod;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class VodServiceImp implements VodService {
    @Override
    public String uploadVideoByStream(MultipartFile file) {
        String fileName = file.getOriginalFilename();//文件原本名字
        String title = fileName.substring(0, fileName.lastIndexOf("."));//上传后在阿里云控制台显示的名字
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET, title, fileName, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        return response.getVideoId();
    }

    @Override
    public void deleteVideoById(String videoId) {
        if(videoId == null || videoId.equals(""))
            return;
        //初始化对象
        DefaultAcsClient client = InitVod.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        //获得对应request对象
        DeleteVideoRequest request = new DeleteVideoRequest();
        //向request设置videoId
        request.setVideoIds(videoId);
        //调用初始化对象方法执行删除
        try {
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }
}
