package com.chandradip.weatherforecastapi.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;


public interface ReactiveWebClientService {

    <T> Mono<T> getForMono(String url, Class<T> tClass, HttpHeaders headers);
    <T> Mono<ResponseEntity<T>> getForMonoEntity(String url, Class<T> tClass, HttpHeaders headers);
}
