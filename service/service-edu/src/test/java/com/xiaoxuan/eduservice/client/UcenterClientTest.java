package com.xiaoxuan.eduservice.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class UcenterClientTest {

    @Autowired
    private UcenterClient ucenterClient;

    @Test
    public void getUserInfo(){
        Map<String, String> info = ucenterClient.getInfo("1380119362949726210");
        System.out.println(info);
    }
}
