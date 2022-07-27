package com.xiaoxuan.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.xiaoxuan")
@MapperScan("com.xiaoxuan.order.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class OrderApplication {
    public static void main(String[] args){
        SpringApplication.run(OrderApplication.class, args);
    }
}
