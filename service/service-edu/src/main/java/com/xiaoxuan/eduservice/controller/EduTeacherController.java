package com.xiaoxuan.eduservice.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoxuan.eduservice.entity.EduTeacher;
import com.xiaoxuan.eduservice.service.EduTeacherService;
import com.xiaoxuan.eduservice.vo.TeacherQuery;
import com.xiaoxuan.servicebase.exceptionhandler.GuliException;
import com.xiaoxuan.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-03-15
 */
@Api("讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation("查询所有讲师")
    @GetMapping("/list")
    public R queryAllTeacher(){
        List<EduTeacher> teacherList = eduTeacherService.list(null);
        R result = null;
        if(teacherList != null || teacherList.size() > 0){
            result = R.ok();
        }else{
            result = R.error();
        }
        result.data("list", teacherList);
        return result;
    }
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("delete/{id}")
    public R deleteById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable(value = "id") String id){
        Boolean flag = eduTeacherService.removeById(id);
        R result = null;
        if(flag){
            result = R.ok();
        }else{
            result = R.error();
        }
        return result;
    }
    @ApiOperation("分页获取讲师")
    @GetMapping("/pageList/{current}/{size}")
    public R pageList(@PathVariable(value = "current") Long current, @PathVariable(value = "size") Long size){
        R result = null;
        //获取page对象,给出当前页、每页记录数两个参数
        Page<EduTeacher> page = new Page<>(current, size);
        //执行page操作，数据封装在page对象内
        eduTeacherService.page(page, null);
        //将数据封装到result
        List<EduTeacher> list = page.getRecords();
        Long total = page.getTotal();//总记录数
        result = R.ok().data("list", list).data("total", total);
        return result;
    }

    /**
     * @ RequestBody 表示前端以json数据传给后端，自动封装为对应java对象
     * required=false表示数据可以为空，使用该注解必须用post提交
     */
    @ApiOperation("分页条件获取讲师")
    @PostMapping("/pageListCondition/{current}/{size}")
    public R pageListCondition(@PathVariable(value = "current") Long current, @PathVariable(value = "size") Long size,
                               @RequestBody(required = false) TeacherQuery teacherQuery){
        R result = null;
        //获取page对象,给出当前页、每页记录数两个参数
        Page<EduTeacher> page = new Page<>(current, size);
        //由querymrapper构建查询条件
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        //由vo对象得到所有条件
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        Integer level = teacherQuery.getLevel();
        String name = teacherQuery.getName();
        //根据条件判断是否为null来添加wrapper条件
        if(begin != null && !"".equals(begin)){
            //大于
            queryWrapper.ge("gmt_create", begin);
        }
        if(end != null && !"".equals(end)){
            //小于
            queryWrapper.le("gmt_create", end);
        }
        if(level != null && !"".equals(level)){
            queryWrapper.eq("level", level);
        }
        if(name != null && !"".equals(name)){
            //名字模糊查询
            queryWrapper.like("name", name);
        }
        queryWrapper.orderByDesc("gmt_create");
        //执行page操作，数据封装在page对象内
        eduTeacherService.page(page, queryWrapper);
        //将数据封装到result
        List<EduTeacher> list = page.getRecords();
        Long total = page.getTotal();//总记录数
        result = R.ok().data("list", list).data("total", total);
        return result;
    }

    /**
     * ID、时间自动填充
     */
    @ApiOperation("添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        Boolean res = eduTeacherService.save(eduTeacher);
        if(res) return R.ok();
        else return R.error();
    }
    @ApiOperation("查询讲师ById")
    @GetMapping("/{id}")
    public R addTeacher(@PathVariable(value = "id") String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        if(teacher != null) return R.ok().data("teacher", teacher);
        else return R.error();
    }
    @ApiOperation("修改讲师")
    @PostMapping("/update")
    public R updateTeacher(@RequestBody EduTeacher teacher){
        boolean flag = eduTeacherService.updateById(teacher);
        if(flag) return R.ok();
        else return R.error();
    }
}

