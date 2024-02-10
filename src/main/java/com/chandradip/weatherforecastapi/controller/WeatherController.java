package com.chandradip.weatherforecastapi.controller;

import com.chandradip.weatherforecastapi.config.HeaderAuthenticationFilter;
import com.chandradip.weatherforecastapi.constants.AppConstants;
import com.chandradip.weatherforecastapi.mappers.AppResponse;
import com.chandradip.weatherforecastapi.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/forecast/")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private HeaderAuthenticationFilter headerAuthenticationFilter;

    /**
     * Retrieve forecast summary by location name.
     *
     * @param city The name of the city.
     * @param clientId The client ID for authentication.
     * @param clientSecret The client secret for authentication.
     * @return ResponseEntity containing the forecast summary.
     */
    @Operation(summary = "Retrieve forecast summary by location name", tags = { "Weather" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of forecast summary", content = @Content(schema = @Schema(implementation = AppResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "City not found")
    })
    @GetMapping("/{city}/summary")
    Mono<ResponseEntity<AppResponse>> getForecastSummaryByLocationName(
            @PathVariable String city,
            @RequestHeader(value = "clientId",required = false) String clientId,
            @RequestHeader(value = "clientSecret", required = false) String clientSecret
    ) {
//        if (!headerAuthenticationFilter.authenticateRequest(clientId, clientSecret)) {
//            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(new AppResponse("Invalid clientId and clientSecret", HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.value())));
//        }
        return weatherService.getForecastSummaryByLocationName(city)
                .map(appResponse -> {
                    if (appResponse.getStatus() == AppConstants.SUCCESS) {
                        return ResponseEntity.ok().body(appResponse);
                    } else {
                        return ResponseEntity.status(appResponse.getStatusCode()).body(appResponse);
                    }
                });
    }

    /**
     * Retrieve hourly forecast by location.
     *
     * @param city The name of the city.
     * @param clientId The client ID for authentication.
     * @param clientSecret The client secret for authentication.
     * @return ResponseEntity containing the hourly forecast.
     */
    @Operation(summary = "Retrieve hourly forecast by location", tags = { "Weather" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of hourly forecast", content = @Content(schema = @Schema(implementation = AppResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "City not found")
    })
    @GetMapping("/{city}/hourly")
    Mono<ResponseEntity<AppResponse>> getHourlyForecastByLocation(
            @PathVariable String city,
            @RequestHeader(value = "clientId", required = false) String clientId,
            @RequestHeader(value = "clientSecret", required = false) String clientSecret
    ) {
        // Implement authentication logic based on clientId and clientSecret
//        if (!headerAuthenticationFilter.authenticateRequest(clientId, clientSecret)) {
//            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(new AppResponse("Invalid clientId and clientSecret", HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.value())));
//        }
        return weatherService.getHourlyForecastByLocationName(city)
                .map(appResponse -> {
                    if (appResponse.getStatus() == AppConstants.SUCCESS) {
                        return ResponseEntity.ok().body(appResponse);
                    } else {
                        return ResponseEntity.status(appResponse.getStatusCode()).body(appResponse);
                    }
                });
    }
}
