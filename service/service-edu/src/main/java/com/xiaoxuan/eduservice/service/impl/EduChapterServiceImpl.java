package com.xiaoxuan.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxuan.eduservice.client.VodClient;
import com.xiaoxuan.eduservice.entity.EduChapter;
import com.xiaoxuan.eduservice.entity.EduVideo;
import com.xiaoxuan.eduservice.entity.chapter.ChapterVo;
import com.xiaoxuan.eduservice.entity.chapter.VideoVo;
import com.xiaoxuan.eduservice.mapper.EduChapterMapper;
import com.xiaoxuan.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxuan.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-03-22
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;
    @Autowired
    private VodClient vodClient;

    @Override
    public List<ChapterVo> getChapter(String courseId) {
        List<ChapterVo> res = new ArrayList<>();
        //得到对应课程id的所有章节
        QueryWrapper<EduChapter> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("course_id", courseId);
//        queryWrapper1.orderByAsc("title");
        List<EduChapter> allChapter = this.list(queryWrapper1);
        //遍历所有章节，对每一个章节查出对应的所有小节
        for(EduChapter eduChapter:allChapter){
            String eduChapterId = eduChapter.getId();
            String eduChapterTitle = eduChapter.getTitle();
            //得到该章节的所有小节
            QueryWrapper<EduVideo> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("chapter_id", eduChapterId);
//            queryWrapper2.orderByAsc("title");
            List<EduVideo> allVideo = videoService.list(queryWrapper2);
            //将所有小节封装成videoVo对象并添加到小节集合中
            List<VideoVo> videoVoList = new ArrayList<>();
            for(EduVideo eduVideo: allVideo){
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(eduVideo, videoVo);
                videoVoList.add(videoVo);
            }
            //将所有信息封装成ChapterVo对象并添加到res集合中
            ChapterVo chapterVo = new ChapterVo(eduChapterId,eduChapterTitle,videoVoList);
            res.add(chapterVo);
        }

        return res;
    }

    @Override
    public boolean removeChapter(String chapterId) {
        //先删除该章节下的所有小节以及所有小节的视频
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        List<EduVideo> videoList = videoService.list(queryWrapper);
        for(EduVideo video:videoList){
            videoService.removeVideoById(video.getId());
        }
        //再删除章节
        boolean res = this.removeById(chapterId);
        return res;
    }
}
