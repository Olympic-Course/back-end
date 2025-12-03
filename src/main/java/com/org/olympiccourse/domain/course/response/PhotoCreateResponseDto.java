package com.org.olympiccourse.domain.course.response;

import com.org.olympiccourse.domain.coursephoto.entity.CoursePhoto;
import lombok.Builder;

@Builder
public record PhotoCreateResponseDto(

    Long photoId,

    String path,

    Boolean isRep) {

    public static PhotoCreateResponseDto of(CoursePhoto photo, String cloudFrontDomain) {
        String fullUrl = "https://" + cloudFrontDomain + "/" + photo.getPath();

        return PhotoCreateResponseDto.builder()
            .photoId(photo.getId())
            .path(fullUrl)
            .isRep(photo.isRep())
            .build();
    }
}
