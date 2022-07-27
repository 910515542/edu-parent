package com.xiaoxuan.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxuan.eduservice.entity.EduSubject;
import com.xiaoxuan.eduservice.entity.subject.ExcelSubjectData;
import com.xiaoxuan.eduservice.service.EduSubjectService;
import com.xiaoxuan.servicebase.exceptionhandler.GuliException;

import java.util.Map;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {
    private EduSubjectService subjectService;

    public  SubjectExcelListener(){ }
    public  SubjectExcelListener(EduSubjectService subjectService){
        this.subjectService = subjectService;
    }


    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        if(excelSubjectData == null) {
            throw new GuliException(20001,"Excel数据为空，添加失败");
        }
        //添加一级分类
        EduSubject eduSubject = this.existOneSubject(excelSubjectData.getOneSubjectName());
        if(eduSubject == null) {//没有相同的
            eduSubject = new EduSubject();
            eduSubject.setTitle(excelSubjectData.getOneSubjectName());
            eduSubject.setParentId("0");
            subjectService.save(eduSubject);
        }

        //获取一级分类id值
        String pid = eduSubject.getId();

        //添加二级分类
        EduSubject existTwoSubject = this.existTwoSubject(excelSubjectData.getTwoSubjectName(), pid);
        if(existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(excelSubjectData.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }
    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //判断一级分类是否重复
    private EduSubject existOneSubject(String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }
    //判断二级分类是否重复
    private EduSubject existTwoSubject(String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id", pid);
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

}
