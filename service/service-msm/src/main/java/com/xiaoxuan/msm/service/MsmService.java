package com.xiaoxuan.msm.service;

import java.util.Map;

public interface MsmService {
    boolean send(Map<String, Object> map, String number);
}
