package com.xiaoxuan.order.service.impl;

import com.xiaoxuan.order.client.ServiceEduClient;
import com.xiaoxuan.order.client.UcenterClient;
import com.xiaoxuan.order.entity.Order;
import com.xiaoxuan.order.mapper.OrderMapper;
import com.xiaoxuan.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxuan.order.util.OrderNoUtil;
import com.xiaoxuan.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-04-19
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private ServiceEduClient serviceEduClient;
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String saveOrder(String courseId, String memberIdByJwtToken) {
        //远程调用课程服务，根据课程id获取课程信息
        Map<String, String> courseMap = serviceEduClient.getCourseInfoDto(courseId);

        //远程调用用户服务，根据用户id获取用户信息
        Map<String, String> userMap = ucenterClient.getUserInfo(memberIdByJwtToken);
        if(courseMap == null || userMap == null)
            throw new GuliException(20001, "获取课程或用户信息失败");
        //创建订单
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseMap.get("courseTitle"));
        order.setCourseCover(courseMap.get("courseCover"));
        order.setTeacherName(courseMap.get("courseTeacher"));
        order.setTotalFee(new BigDecimal(courseMap.get("coursePrice")));
        order.setMemberId(memberIdByJwtToken);
        order.setMobile(userMap.get("userMobile"));
        order.setNickname(userMap.get("nickname"));
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
