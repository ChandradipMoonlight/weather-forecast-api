package com.chandradip.weatherforecastapi.exception;

import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

public class InvalidOrExpiredTokenException extends RuntimeException{

    public InvalidOrExpiredTokenException(String message) {
        super(message);
    }
//
//
//    /**
//     * Create a new {@code AbstractErrorWebExceptionHandler}.
//     *
//     * @param errorAttributes    the error attributes
//     * @param resources          the resources configuration properties
//     * @param applicationContext the application context
//     * @since 2.4.0
//     */
//    public InvalidOrExpiredTokenException(ErrorAttributes errorAttributes,
//                                          WebProperties.Resources resources,
//                                          ApplicationContext applicationContext,
//                                          ServerCodecConfigurer configurer) {
//        super(errorAttributes, resources, applicationContext);
//    }
//
//    @Override
//    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
//        return RouterFunctions.route(RequestPredicates.all(), this::renderException);
//    }
//
//    private Mono<ServerResponse> renderException(ServerRequest request) {
//        Map<String, Object> error = getErrorAttributes(request, ErrorAttributeOptions.defaults());
//        return ServerResponse.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(error));
//    }


}
