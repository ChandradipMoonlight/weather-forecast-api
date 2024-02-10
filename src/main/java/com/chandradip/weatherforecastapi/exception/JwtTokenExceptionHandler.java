package com.chandradip.weatherforecastapi.exception;

import com.chandradip.weatherforecastapi.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.BufferedWriter;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ProblemDetail problemDetail = null;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // Default status

        if (ex instanceof SignatureException) {
            status = HttpStatus.FORBIDDEN;
            problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
            problemDetail.setProperties(Map.of("Access Denied", "Access Token Not valid!"));
        } else if (ex instanceof ExpiredJwtException) {
            status = HttpStatus.FORBIDDEN;
            problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
            problemDetail.setProperties(Map.of("Access Denied", "Access Token Expired!"));
        } else {
            // Handle other exceptions
            log.error("Unhandled exception occurred", ex);
            problemDetail = ProblemDetail.forStatusAndDetail(status, "Internal Server Error");
        }

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] responseBytes = convertObjectToJsonBytes(problemDetail, objectMapper);

        DataBufferFactory dataBufferFactory = response.bufferFactory();
        DataBuffer buffer = dataBufferFactory.wrap(responseBytes);

        return response.writeWith(Mono.just(buffer))
                .doOnError(error -> DataBufferUtils.release(buffer));
    }

    // Utility method to convert Object to JSON bytes
    private byte[] convertObjectToJsonBytes(Object object, ObjectMapper mapper) {
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("Error converting object to JSON bytes", e);
            return new byte[0];
        }
    }
}

