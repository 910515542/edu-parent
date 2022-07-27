package com.xiaoxuan.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxuan.cms.entity.CrmBanner;
import com.xiaoxuan.cms.mapper.CrmBannerMapper;
import com.xiaoxuan.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-04-02
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(key = "'indexBanner'", value = "banner")
    @Override
    public List<CrmBanner> getAllBanner(Object o) {
        QueryWrapper queryWrapper = new QueryWrapper();
        //创建时间降序
        queryWrapper.orderByDesc("gmt_create");
        //在最后拼接sql语句，只查询前3条
        queryWrapper.last("limit 3");
        return this.list(null);
    }
}
