package com.xiaoxuan.eduservice.service;

import com.xiaoxuan.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxuan.eduservice.entity.subject.SubjectOne;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-03-22
 */
public interface EduSubjectService extends IService<EduSubject> {

    void importSubject(MultipartFile file, EduSubjectService subjectService);

    List<SubjectOne> allClassification();
}
