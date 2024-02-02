package com.chandradip.weatherforecastapi.config;

import com.chandradip.weatherforecastapi.mappers.AppConstants;
import com.chandradip.weatherforecastapi.mappers.AppResponse;
import com.chandradip.weatherforecastapi.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class HeaderAuthenticationFilter {
    @Value("${app.security.client-id}")
    private String clientId;

    @Value("${app.security.client-secret}")
    private String clientSecret;

//    public HandlerFilterFunction<ServerResponse, ServerResponse> authenticationFilter() {
//         return (request, next) -> {
//            String incomingClientId = request.headers().header("clientId").get(0);
//            String incomingClientSecret = request.headers().header("clientSecret").get(0);
//            log.info("IncomingClientId : {}, incomingClientSecret : {}", incomingClientId, incomingClientSecret);
//            if (clientId.equals(incomingClientId) && clientSecret.equals(incomingClientSecret)) {
//                return next.handle(request);
//            } else {
//                return ServerResponse.status(HttpStatus.UNAUTHORIZED)
//                        .bodyValue(new AppResponse("Invalid client credentials", HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.value()));
//            }
//        };
//    }

    public boolean authenticateRequest(String clientId, String clientSecret) {
        return this.clientId.equals(clientId) && this.clientSecret.equals(clientSecret);
    }

}
