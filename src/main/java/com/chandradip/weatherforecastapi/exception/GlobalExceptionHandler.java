package com.chandradip.weatherforecastapi.exception;

import com.chandradip.weatherforecastapi.mappers.AppErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<ResponseEntity<AppErrors>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> exceptionInfo = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(objectError -> {
            String fieldName = ((FieldError) objectError).getField();
            exceptionInfo.put(fieldName, objectError.getDefaultMessage());
        });
        return Mono.just(ResponseEntity.badRequest().body(new AppErrors(exceptionInfo)));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<AppErrors>> handleResourceNotFoundException(ResourceNotFoundException e) {
        return Mono.just(ResponseEntity.badRequest().body(new AppErrors(e.getMessage())));
    }

    @ExceptionHandler(InvalidOrExpiredTokenException.class)
    public Mono<ResponseEntity<AppErrors>> handleInvalidOrExpiredTokenException(InvalidOrExpiredTokenException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("token", e.getMessage()); // Assuming 'token' is the field name
        log.info("Handling InvalidOrExpiredTokenException : {}", errorMap);
        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AppErrors(errorMap)));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<AppErrors>> handleGlobalException(Exception e) {
        return Mono.just(ResponseEntity.internalServerError().body(new AppErrors(e.getMessage())));
    }
}
