package com.org.olympiccourse.domain.event.controller;

import com.org.olympiccourse.domain.event.code.EventResponseCode;
import com.org.olympiccourse.domain.event.dto.HomeEventResponse;
import com.org.olympiccourse.domain.event.service.EventService;
import com.org.olympiccourse.global.response.ApiResponse;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<ApiResponse<HomeEventResponse>> getEventData(
        @RequestParam(required = false) LocalDate date) {
        HomeEventResponse result = eventService.getEventData(date);
        return ResponseEntity.ok(ApiResponse.success(EventResponseCode.EVENT_GET_SUCCESS, result));
    }
}
