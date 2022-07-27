package com.xiaoxuan.eduservice.service;

import com.xiaoxuan.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-03-22
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean removeVideoById(String videoId);
}
