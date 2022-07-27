package com.xiaoxuan.order.service;

import com.xiaoxuan.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-04-19
 */
public interface PayLogService extends IService<PayLog> {

    Map<String, String> queryPayStatus(String orderNo);

    Map createNative(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}
