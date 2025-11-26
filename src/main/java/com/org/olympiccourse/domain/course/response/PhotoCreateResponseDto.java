package com.org.olympiccourse.domain.course.response;

import com.org.olympiccourse.domain.coursephoto.entity.CoursePhoto;
import lombok.Builder;

@Builder
public record PhotoCreateResponseDto(

    Long photoId,

    String path,

    Boolean isRep) {

    public static PhotoCreateResponseDto from(CoursePhoto photo) {
        return PhotoCreateResponseDto.builder()
            .photoId(photo.getId())
            .path(photo.getPath())
            .isRep(photo.isRep())
            .build();
    }
}
