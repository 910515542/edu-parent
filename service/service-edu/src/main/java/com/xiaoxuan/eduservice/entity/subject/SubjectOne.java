package com.xiaoxuan.eduservice.entity.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectOne {
    private String title;
    private String id;

    private List<SubjectTwo> subjectTwos;
}
