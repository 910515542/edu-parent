package com.xiaoxuan;


import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @since 2018/12/13
 */
public class EasyExcelTest {

    public static void main(String args[]){
        // 写法1
        String fileName = "C:\\upload\\studentTest.xlsx";//使用“/”不用转义，使用“\”需要写两个\\表示转义
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
//        EasyExcel.write(fileName, StudentTest.class).sheet("学生表一").doWrite(data());
        //读Excel文件
        EasyExcel.read(fileName, StudentTest.class, new ExcelListener()).sheet().doRead();
    }
    private static List<StudentTest> data() {
        List<StudentTest> list = new ArrayList<StudentTest>();
        for (int i = 0; i < 10; i++) {
            StudentTest data = new StudentTest();
            data.setSno(i);
            data.setSname("张三"+i);
            list.add(data);
        }
        return list;
    }
}
