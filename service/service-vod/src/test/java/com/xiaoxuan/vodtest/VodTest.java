package com.xiaoxuan.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class VodTest {
    //测试获取播放地址
    @Test
    public void getAddress(){
        //初始化客户端、请求对象和相应对象
        DefaultAcsClient client = InitVod.initVodClient("LTAI5tBndgVdi88PrAznKSq6", "7pf2MMPUQJBXmNrPrPEkxDLdcSaXN8");
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            //向request设置视频ID
            request.setVideoId("e891b8974a4c4d0cb29feb0ba3b69201");
            //获取请求响应
            response = client.getAcsResponse(request);
            //输出请求结果
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
    //获取视频凭证
    @Test
    public void getAuth(){
        //初始化客户端、请求对象和相应对象
        DefaultAcsClient client = InitVod.initVodClient("LTAI5tBndgVdi88PrAznKSq6", "7pf2MMPUQJBXmNrPrPEkxDLdcSaXN8");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            //向request设置视频ID
            request.setVideoId("e891b8974a4c4d0cb29feb0ba3b69201");
            //调用初始化对象方法获取请求响应
            response = client.getAcsResponse(request);
            //得到凭证
            String playAuth = response.getPlayAuth();
            System.out.print("PlayAuth = " + playAuth + "\n");

        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
    }
    //本地文件上传
    @Test
    public void testUploadVideo(){
        String accessKeyId = "LTAI5tBndgVdi88PrAznKSq6";
        String accessKeySecret = "7pf2MMPUQJBXmNrPrPEkxDLdcSaXN8";
        String title = "6 - What If I Want to Move Faster from SDK";
        String fileName = "C:\\面试\\在线教育网站\\项目资料\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4";
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

}
