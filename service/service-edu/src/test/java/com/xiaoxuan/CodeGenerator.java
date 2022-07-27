package com.xiaoxuan;

import com.xiaoxuan.eduservice.client.UcenterClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class CodeGenerator {

    @Autowired
    private UcenterClient ucenterClient;

    @Test
    public void run() {
        Map<String, String> info = ucenterClient.getInfo("1380119362949726210");
        System.out.println(info);
    }
}
