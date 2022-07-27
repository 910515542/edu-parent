package com.xiaoxuan.eduservice.service.impl;

import com.xiaoxuan.eduservice.client.VodClient;
import com.xiaoxuan.eduservice.entity.EduVideo;
import com.xiaoxuan.eduservice.mapper.EduVideoMapper;
import com.xiaoxuan.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxuan.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-03-22
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public boolean removeVideoById(String videoId) {
        //调用vod微服务将小节对应的云端视频删除
        EduVideo video = this.getById(videoId);
        String videoSourceId = video.getVideoSourceId();
        R res = R.ok();
        if(!StringUtils.isEmpty(videoSourceId))
            res = vodClient.removeVideo(videoSourceId);
        //查看vod服务调用情况：成功对应R.ok()、普通失败对应R.error()或服务熔断对应R.error.message("time out")
        System.out.println("Vod服务调用结果信息："+res.getMessage());
        //vod服务调用或删除失败，直接返回失败信息
        if(!res.getSuccess()){
            return false;
        }
        //将小节信息删除
        boolean res2 = this.removeById(videoId);
        return res2;
    }
}
