package com.xiaoxuan.statistics.service;

import com.xiaoxuan.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-04-21
 */
public interface DailyService extends IService<Daily> {

    void createStatisticsByDay(String day);

    Map<String, Object> getChartData(String begin, String end, String type);
}
