package com.chandradip.weatherforecastapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
