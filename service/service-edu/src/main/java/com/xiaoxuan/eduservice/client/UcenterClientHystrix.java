package com.xiaoxuan.eduservice.client;

import com.xiaoxuan.utils.R;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Member;
import java.util.Map;

@Component
public class UcenterClientHystrix implements UcenterClient {

    @Override
    public Map<String, String> getInfo(String id) {
        return null;
    }

    @Override
    public R getUserInfo(HttpServletRequest request) {
        return null;
    }
}
