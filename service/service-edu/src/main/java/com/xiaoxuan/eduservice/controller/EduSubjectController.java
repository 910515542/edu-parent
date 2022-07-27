package com.xiaoxuan.eduservice.controller;


import com.xiaoxuan.eduservice.service.EduSubjectService;
import com.xiaoxuan.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-03-22
 */
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @ApiOperation("通过Excel导入课程的一二级分类")
    @PostMapping("/import")
    public R importSubjectByExcel(MultipartFile file){
        subjectService.importSubject(file, subjectService);
        return R.ok();
    }

    @ApiOperation("得到所有类别")
    @GetMapping("/allClassification")
    public R getAllClassification(){
        return R.ok().data("list", subjectService.allClassification());
    }

}

