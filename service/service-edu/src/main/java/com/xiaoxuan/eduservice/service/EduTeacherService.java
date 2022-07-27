package com.xiaoxuan.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoxuan.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-03-15
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> pageListWeb(Page<EduTeacher> pageParam);
}
