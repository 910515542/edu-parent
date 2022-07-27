package com.xiaoxuan.eduservice.entity.chapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoVo {
    private String id;
    private String title;

    private Integer isFree;
    private String videoSourceId;
}
