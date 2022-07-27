package com.xiaoxuan.eduservice.controller;

import com.xiaoxuan.utils.R;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {
    @PostMapping("/login")
    public R login(){
        R res = null;
        res = R.ok().data("token", "admin");
        return res;
    }
    @GetMapping("/info")
    public R info(){
        R res = null;
        HashMap<String, Object> map = new HashMap<>();
        map.put("roles", "[admin]");
        map.put("name","管理员");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        res = R.ok().data(map);
        return res;
    }
}
