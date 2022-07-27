package com.xiaoxuan.acl.controller;



import com.xiaoxuan.acl.entity.Permission;
import com.xiaoxuan.acl.service.PermissionService;
import com.xiaoxuan.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */

@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class PermissionController {

    public static final String INIT_PID = "0";

    @Autowired
    private PermissionService permissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/list")
    public R indexAllPermission() {
        long start = System.currentTimeMillis();
        List<Permission> list =  permissionService.queryAllMenuGuli();
        long end = System.currentTimeMillis();
//        System.out.println("1耗时:" + (end- start));
//        start = System.currentTimeMillis();
//        list =  permissionService.queryAllMenuGuli(INIT_PID);
//        end = System.currentTimeMillis();
//        System.out.println("2耗时:" + (end - start));
        return R.ok().data("children",list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable String id) {
        permissionService.removeChildByIdGuli(id);
        return R.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public R doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
        return R.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public R toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return R.ok().data("children", list);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("/save")
    public R save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return R.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("/update")
    public R updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return R.ok();
    }

}

