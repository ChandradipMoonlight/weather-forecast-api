package com.chandradip.weatherforecastapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {
//    @Autowired
//    private HeaderAuthenticationFilter headerAuthenticationFilter;
//
//    @Bean
//    public RouterFunction<ServerResponse> route() {
//        return RouterFunctions.route()
//                .path("/api/v1/forecast", builder ->
//                        builder
//                                .GET("/{city}/summary", request -> ServerResponse.ok().build())
//                                .GET("/{city}/hourly", request -> ServerResponse.ok().build())
//                                .filter(headerAuthenticationFilter.authenticationFilter())
//                )
//                .build();
//    }
}
