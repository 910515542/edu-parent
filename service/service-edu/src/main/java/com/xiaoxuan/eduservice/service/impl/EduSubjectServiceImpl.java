package com.xiaoxuan.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxuan.eduservice.entity.EduSubject;
import com.xiaoxuan.eduservice.entity.subject.ExcelSubjectData;
import com.xiaoxuan.eduservice.entity.subject.SubjectOne;
import com.xiaoxuan.eduservice.entity.subject.SubjectTwo;
import com.xiaoxuan.eduservice.listener.SubjectExcelListener;
import com.xiaoxuan.eduservice.mapper.EduSubjectMapper;
import com.xiaoxuan.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-03-22
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void importSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            //开始读Excel，但是读的过程与处理读出来的数据需要在监听器里具体操作。
            EasyExcel.read(inputStream, ExcelSubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<SubjectOne> allClassification() {
        List<SubjectOne> list = new ArrayList<>();
        //得到所有一级分类
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", "0");
        List<EduSubject> eduSubjects = this.list(queryWrapper);
        //遍历所有一级分类得到每个所属的所有二级分类
        for(EduSubject eduSubject:eduSubjects){
            //得到一级分类下的所有二级分类
            QueryWrapper<EduSubject> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("parent_id", eduSubject.getId());
            List<EduSubject> eduSubjects2 = this.list(queryWrapper2);
            //创建一级分类对象并完善其属性
            SubjectOne subjectOne = new SubjectOne();
            subjectOne.setId(eduSubject.getId());
            subjectOne.setTitle(eduSubject.getTitle());
            //遍历所有二级分类创建对应的二级分类对象并添加到二级分类List中
            List<SubjectTwo> subjectTwos = new ArrayList<>();
            for(EduSubject eduSubject2Temp:eduSubjects2){
                SubjectTwo subjectTwo = new SubjectTwo();
                subjectTwo.setId(eduSubject2Temp.getId());
                subjectTwo.setTitle(eduSubject2Temp.getTitle());
                subjectTwos.add(subjectTwo);
            }
            //设置一级分类对象最后一个属性
            subjectOne.setSubjectTwos(subjectTwos);
            list.add(subjectOne);
        }
        return list;
    }
}
