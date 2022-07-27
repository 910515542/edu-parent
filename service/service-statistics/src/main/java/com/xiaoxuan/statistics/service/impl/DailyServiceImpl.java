package com.xiaoxuan.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxuan.servicebase.exceptionhandler.GuliException;
import com.xiaoxuan.statistics.client.UserClient;
import com.xiaoxuan.statistics.entity.Daily;
import com.xiaoxuan.statistics.mapper.DailyMapper;
import com.xiaoxuan.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxuan.utils.R;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-04-21
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UserClient userClient;

    @Override
    @Transactional
    public void createStatisticsByDay(String day) {
        //删除已存在的统计对象
        QueryWrapper<Daily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.eq("date_calculated", day);
        baseMapper.delete(dayQueryWrapper);


        //获取统计信息
        R res = userClient.registerCount(day);
        if(res == null){
            throw new GuliException(20001, "用户服务不可用");
        }
        Map<String, Object> map = res.getData();
        Integer registerNum = (Integer) map.get("countRegister");
        Integer loginNum = RandomUtils.nextInt(100, 200);
        Integer videoViewNum = RandomUtils.nextInt(100, 200);
        Integer courseNum = RandomUtils.nextInt(100, 200);

        //创建统计对象
        Daily daily = new Daily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {
        //开始时间与结束时间段的统计数据
        QueryWrapper<Daily> dayQueryWrapper = new QueryWrapper<>();
        //时间与指定的列
        dayQueryWrapper.select(type, "date_calculated");
        dayQueryWrapper.between("date_calculated", begin, end);

        List<Daily> dayList = baseMapper.selectList(dayQueryWrapper);

        Map<String, Object> map = new HashMap<>();
        List<Integer> dataList = new ArrayList<>();
        List<String> dateList = new ArrayList<String>();
        //将时间段的每一天时间和数据相互对应的放入两个列表中，
        for (int i = 0; i < dayList.size(); i++) {
            Daily daily = dayList.get(i);
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        map.put("dataList", dataList);
        map.put("dateList", dateList);

        return map;
    }
}
