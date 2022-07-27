package com.xiaoxuan.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.xiaoxuan")//能够扫描到common模块的类
@MapperScan("com.xiaoxuan.cms.mapper")
@EnableDiscoveryClient
public class CmsApplication {
    public static void main(String args[]){
        SpringApplication.run(CmsApplication.class, args);
    }
}
