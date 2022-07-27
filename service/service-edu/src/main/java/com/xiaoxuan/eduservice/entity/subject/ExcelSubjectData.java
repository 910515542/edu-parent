package com.xiaoxuan.eduservice.entity.subject;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelSubjectData {
    /**
     * 作为接收EasyExcel读到的每一行数据的对象，index指定该字段对应
     * Excel中的一列。
     * Excel表每一行表示一个一级分类的类名和其之下的二级分类的类名
     */
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
