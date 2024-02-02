package com.chandradip.weatherforecastapi.controller;

import com.chandradip.weatherforecastapi.mappers.AppConstants;
import com.chandradip.weatherforecastapi.mappers.AppResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health")
    public Mono<ResponseEntity<AppResponse>> checkHealth() {
        return Mono.just("Welcome to Weather Forecase API")
                .map(data-> ResponseEntity.ok().body(new AppResponse(data, AppConstants.SUCCESS)));
    }

}
