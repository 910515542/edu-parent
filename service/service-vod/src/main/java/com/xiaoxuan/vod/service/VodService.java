package com.xiaoxuan.vod.service;

import org.springframework.web.multipart.MultipartFile;


public interface VodService {
    String uploadVideoByStream(MultipartFile file);
    void deleteVideoById(String videoId);
}
