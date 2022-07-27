package com.xiaoxuan;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//创建读取excel监听器
public class ExcelListener extends AnalysisEventListener<StudentTest> {

    //创建list集合封装最终的数据
    List<StudentTest> list = new ArrayList<StudentTest>();

    //一行一行去读取excle内容
    @Override
    public void invoke(StudentTest user, AnalysisContext analysisContext) {
        System.out.println("***"+user);
        list.add(user);
    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}