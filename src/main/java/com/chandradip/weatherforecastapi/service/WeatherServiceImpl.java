package com.chandradip.weatherforecastapi.service;

import com.chandradip.weatherforecastapi.config.HttpHeaderConfig;
import com.chandradip.weatherforecastapi.mappers.AppConstants;
import com.chandradip.weatherforecastapi.mappers.AppResponse;
import com.chandradip.weatherforecastapi.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;

import static java.util.Map.entry;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService{

    @Value("${rapidapi.baseurl}")
    private String rapidBaseApiUrl;

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    @Value("${rapidapi.host}")
    private String rapidApiHost;

    @Autowired
    private HttpHeaderConfig headerConfig;
    @Autowired
    private ReactiveWebClientService reactiveWebClientService;
    @Override
    public Mono<AppResponse> getForecastSummaryByLocationName(String location) {
        String url = rapidBaseApiUrl + location + AppConstants.RAPID_API_END_POINT_SUMMARY;
        log.info("URL : {}", url);
        return reactiveWebClientService.getForMono(url, JsonNode.class, getRapidApiHeaders())
                .map(jsonNode -> new AppResponse(jsonNode, AppConstants.SUCCESS))
                .onErrorResume(e -> {
                    log.error("Error in getForecastSummaryByLocationName : {}", e.getMessage());
                    return Mono.just(new AppResponse(e.getMessage(), AppConstants.FAIL, getStatusCodeFromException(e)));
                }).log();
    }

    @Override
    public Mono<AppResponse> getHourlyForecastByLocationName(String city) {
        String url = rapidBaseApiUrl+city+AppConstants.RAPID_API_END_POINT_HOURLY;
        return reactiveWebClientService.getForMono(url, JsonNode.class, getRapidApiHeaders())
                .map(jsonNode -> new AppResponse(jsonNode, AppConstants.SUCCESS))
                .onErrorResume(e -> {
                    log.error("Error in getHourlyForecastByLocationName : {}", e.getMessage());
                    return Mono.just(new AppResponse(e.getMessage(), AppConstants.FAIL, getStatusCodeFromException(e)));
                });
    }


    private HttpHeaders getRapidApiHeaders() {
        HttpHeaders headers = headerConfig.setHeaders(Map.ofEntries(
                entry(AppConstants.X_RAPID_API_KEY, rapidApiKey),
                entry(AppConstants.X_RAPID_API_HOST, rapidApiHost)
        ));
        log.info("Headers : {}", JsonUtils.javaToJson(headers));
        return headers;
    }

    private int getStatusCodeFromException(Throwable e) {
        int statusCode = 0;
        if (e instanceof WebClientResponseException) {
            WebClientResponseException webClientResponseException = (WebClientResponseException) e;
            statusCode = webClientResponseException.getStatusCode().value();
        }
        return statusCode;
    }
}
