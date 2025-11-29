package com.org.olympiccourse.domain.course.response;

import com.org.olympiccourse.domain.tag.entity.Tag;
import java.util.List;
import lombok.Getter;

@Getter
public class CourseOverviewTagResponseDto {

    private Long courseId;

    private String thumbnail;

    private String title;

    private List<Tag> tags;

    private String writer;

    private Long likeNum;

    private Boolean liked;

    public CourseOverviewTagResponseDto(Long courseId, String thumbnail, String title, String writer,
        Long likeNum, Boolean liked) {

        this.courseId = courseId;
        this.thumbnail = thumbnail;
        this.title = title;
        this.writer = writer;
        this.likeNum = likeNum;
        this.liked = liked;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
