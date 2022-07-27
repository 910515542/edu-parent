package com.xiaoxuan.acl;

import com.xiaoxuan.acl.entity.Permission;
import com.xiaoxuan.acl.service.PermissionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class PermissionServiceImpTest{
    @Autowired
    private PermissionService permissionService;

    @Test
    void queryAllMenuGuliTest(){
        long start = System.currentTimeMillis();
        List<Permission> permissionList = permissionService.queryAllMenuGuli();
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - start) + "毫秒");
    }
}
