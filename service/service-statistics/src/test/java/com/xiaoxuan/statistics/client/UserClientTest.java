package com.xiaoxuan.statistics.client;

import com.xiaoxuan.utils.R;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserClientTest {
    @Autowired
    private UserClient userClient;

    @Test
    public void registerCountTest(){
        R r = userClient.registerCount("2021-06-25");
        System.out.println(r);
    }
}
