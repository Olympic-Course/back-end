package com.org.olympiccourse.domain.home.controller;

import com.org.olympiccourse.domain.home.code.HomeResponseCode;
import com.org.olympiccourse.domain.home.dto.HomeResponse;
import com.org.olympiccourse.domain.home.service.HomeService;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.global.annotation.LoginUser;
import com.org.olympiccourse.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {

    private final HomeService homeService;

    @GetMapping
    public ResponseEntity<ApiResponse<HomeResponse>> home(@LoginUser User user){

        HomeResponse result = homeService.getHomeData(user);
        return ResponseEntity.ok(ApiResponse.success(HomeResponseCode.HOME_GET_SUCCESS, result));
    }
}
