package com.xiaoxuan.order.service;

import com.xiaoxuan.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-04-19
 */
public interface OrderService extends IService<Order> {

    String saveOrder(String courseId, String memberIdByJwtToken);
}
