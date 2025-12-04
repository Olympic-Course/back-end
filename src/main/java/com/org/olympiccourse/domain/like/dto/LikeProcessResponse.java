package com.org.olympiccourse.domain.like.dto;

public record LikeProcessResponse(
    Boolean liked,
    Long likeNum
) {

}
