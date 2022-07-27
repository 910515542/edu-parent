package com.xiaoxuan.order.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 订单号工具类
 *
 * @author xiaoxuan
 * @since 1.0
 */
public class OrderNoUtil {

    /**
     * 生成订单号：订单时间+三位随机数
     * @return
     */
    public static String getOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        return newDate + result;
    }

}
