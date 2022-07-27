package com.xiaoxuan.eduservice.entity.chapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterVo {
    private String id;
    private String title;

    private List<VideoVo> videoVoList;
}
