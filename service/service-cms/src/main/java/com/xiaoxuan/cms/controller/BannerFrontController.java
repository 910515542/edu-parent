package com.xiaoxuan.cms.controller;


import com.xiaoxuan.cms.entity.CrmBanner;
import com.xiaoxuan.cms.service.CrmBannerService;
import com.xiaoxuan.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/cms/bannerFront")
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取首页banner")
    @GetMapping("/getAllBanner")
    public R index() {
        List<CrmBanner> list = bannerService.getAllBanner(null);
        return R.ok().data("list", list);
    }
}

