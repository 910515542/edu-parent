package com.xiaoxuan.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxuan.order.entity.Order;
import com.xiaoxuan.order.service.OrderService;
import com.xiaoxuan.utils.JwtUtils;
import com.xiaoxuan.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-04-19
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @ApiOperation("根据课程id和用户id创建订单，返回订单id")
    @PostMapping("createOrder/{courseId}")
    public R save(@PathVariable String courseId, HttpServletRequest request) {
        //调用业务层使用前端传过来的课程ID和根据请求体中的token获取的用户ID来创建订单
        String orderId = orderService.saveOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderId);
    }

    @ApiOperation("获取对应订单ID的订单信息")
    @GetMapping("getOrder/{orderId}")
    public R get(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    @ApiOperation("远程调用：根据课程id与用户id查找订单")
    @GetMapping("/isBuyCourse/{memberId}/{courseId}")
    public Boolean isBuyCourse(@PathVariable String memberId,
                               @PathVariable String courseId) {
        //订单状态是1表示支付成功
        int count = orderService.count(new QueryWrapper<Order>().eq("member_id", memberId).eq("course_id", courseId).eq("status", 1));
        System.out.println("是否存在对应订单："+count);
        if(count>0) {
            return true;
        } else {
            return false;
        }
    }



}

