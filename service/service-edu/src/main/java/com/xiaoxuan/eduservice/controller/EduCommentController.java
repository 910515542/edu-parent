package com.xiaoxuan.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoxuan.eduservice.client.UcenterClient;
import com.xiaoxuan.eduservice.entity.EduComment;
import com.xiaoxuan.eduservice.service.EduCommentService;
import com.xiaoxuan.servicebase.exceptionhandler.GuliException;
import com.xiaoxuan.utils.JwtUtils;
import com.xiaoxuan.utils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-04-18
 */
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;
    @Autowired
    private UcenterClient ucenterClient;

    //根据课程id查询评论列表
    @ApiOperation(value = "评论分页列表")
    @GetMapping("/list/{page}/{limit}/{courseId}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseId", value = "评论所属课程ID")
            @PathVariable String courseId) {
        //使用mybatis分页插件执行分页
        Page<EduComment> pageParam = new Page<>(page, limit);

        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByDesc("gmt_create");
        commentService.page(pageParam,wrapper);
        List<EduComment> commentList = pageParam.getRecords();
        //封装分页数据给前端
        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return R.ok().data(map);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("/save")
    public R save(@RequestBody EduComment comment, HttpServletRequest request) {
        //从前端请求request中获取到token里的用户ID
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().message("请先登录");
        }
        System.out.println("memberId:" + memberId);
        comment.setMemberId(memberId);
        //调用用户中心微服务得到该用户的一些基本信息
        Map<String, String> userInfo = ucenterClient.getInfo(memberId);
        R res = ucenterClient.getUserInfo(request);
        System.out.println("userInfo:" + userInfo);
        System.out.println("r:" + res);
        //微服务熔断处理
        if(userInfo == null) return R.error().message("用户中心不可用");
        //将评论对象添加到数据库
        comment.setNickname(userInfo.get("nickname"));
        comment.setAvatar(userInfo.get("avatar"));
        commentService.save(comment);

        return R.ok();
    }
}

