package com.xiaoxuan.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoxuan.cms.entity.CrmBanner;
import com.xiaoxuan.cms.service.CrmBannerService;
import com.xiaoxuan.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/cms/bannerAdmin")
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation("分页获取轮播图")
    @GetMapping("/bannerList/{page}/{size}")
    public R bannerListByPage(@PathVariable("page") int currentPage,
                              @PathVariable("size") int size)
    {
        Page<CrmBanner> page = new Page<>(currentPage, size);
        bannerService.page(page, null);
        return R.ok().data("list", page.getRecords()).data("total", page.getTotal());
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("/get/{id}")
    public R get(@PathVariable("id") String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("/save")
    public R save(@RequestBody CrmBanner banner) {
        bannerService.save(banner);
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("/update")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        bannerService.removeById(id);
        return R.ok();
    }

}

