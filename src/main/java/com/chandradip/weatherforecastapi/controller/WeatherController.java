package com.chandradip.weatherforecastapi.controller;

import com.chandradip.weatherforecastapi.mappers.AppConstants;
import com.chandradip.weatherforecastapi.mappers.AppResponse;
import com.chandradip.weatherforecastapi.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/forecast/")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{city}/summary")
    Mono<ResponseEntity<AppResponse>> getForecastSummaryByLocationName(@PathVariable String city) {
        return weatherService.getForecastSummaryByLocationName(city)
                .map(appResponse -> {
                    if (appResponse.getStatus()== AppConstants.SUCCESS) {
                        return ResponseEntity.ok().body(appResponse);
                    } else {
                        return ResponseEntity.status(appResponse.getStatusCode()).body(appResponse);
                    }
                } );
    }

    @GetMapping("/{city}/hourly")
    Mono<ResponseEntity<AppResponse>> getHourlyForecastByLocation(@PathVariable String city) {
        return weatherService.getHourlyForecastByLocationName(city)
                .map(appResponse -> {
                    if (appResponse.getStatus()== AppConstants.SUCCESS) {
                        return ResponseEntity.ok().body(appResponse);
                    } else {
                        return ResponseEntity.status(appResponse.getStatusCode()).body(appResponse);
                    }
                });
    }
}
