package com.org.olympiccourse.domain.course.response;

import com.org.olympiccourse.domain.coursestep.entity.CourseStep;
import java.util.List;
import lombok.Builder;

@Builder
public record StepResponseDto(

    Long stepId,

    Integer stepOrder,

    String name,

    Double latitude,

    Double longitude,

    String descriptionKo,

    List<PhotoCreateResponseDto> photos
) {

    public static StepResponseDto from(CourseStep step) {
        List<PhotoCreateResponseDto> photoDtos = step.getPhotos()
            .stream().map(photo -> PhotoCreateResponseDto.builder()
                .photoId(photo.getId())
                .path(photo.getPath())
                .isRep(photo.isRep())
                .build())
            .toList();

        return StepResponseDto.builder()
            .stepId(step.getId())
            .stepOrder(step.getStepOrder())
            .name(step.getName())
            .latitude(step.getLatitude())
            .longitude(step.getLongitude())
            .descriptionKo(step.getDescriptionKo())
            .photos(photoDtos)
            .build();
    }

}
