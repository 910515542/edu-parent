package com.xiaoxuan.cms.service;

import com.xiaoxuan.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-04-02
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> getAllBanner(Object o);
}
