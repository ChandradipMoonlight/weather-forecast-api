package com.chandradip.weatherforecastapi.service;

import com.chandradip.weatherforecastapi.mappers.AppResponse;
import reactor.core.publisher.Mono;

public interface WeatherService {

    Mono<AppResponse> getForecastSummaryByLocationName(String location);

    Mono<AppResponse> getHourlyForecastByLocationName(String city);
}
