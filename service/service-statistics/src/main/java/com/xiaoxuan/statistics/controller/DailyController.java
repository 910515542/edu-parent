package com.xiaoxuan.statistics.controller;


import com.xiaoxuan.statistics.service.DailyService;
import com.xiaoxuan.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-04-21
 */
@RestController
@RequestMapping("/statistics/daily")
public class DailyController {
    @Autowired
    private DailyService dailyService;

    @ApiOperation("获取统计数据并插入到统计分析表中")
    @PostMapping("/getData/{day}")
    public R getStatisticsData(@PathVariable("day") String day){
        dailyService.createStatisticsByDay(day);
        return R.ok();
    }

    @ApiOperation("获取统计分析表数据")
    @GetMapping("/show/{begin}/{end}/{type}")
    public R showChart(@PathVariable String begin,@PathVariable String end,@PathVariable String type){
        Map<String, Object> map = dailyService.getChartData(begin, end, type);
        return R.ok().data(map);
    }
}

