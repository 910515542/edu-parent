package com.xiaoxuan.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.xiaoxuan"})
@SpringBootApplication//取消数据源自动配置
//@MapperScan("com.xiaoxuan.ucenter.mapper")这里添加mapper扫描或者在mapper类上添加mapper注解
@EnableDiscoveryClient
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }

}
