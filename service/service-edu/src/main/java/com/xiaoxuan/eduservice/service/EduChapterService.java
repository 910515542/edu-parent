package com.xiaoxuan.eduservice.service;

import com.xiaoxuan.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxuan.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-03-22
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapter(String courseId);

    boolean removeChapter(String chapterId);
}
