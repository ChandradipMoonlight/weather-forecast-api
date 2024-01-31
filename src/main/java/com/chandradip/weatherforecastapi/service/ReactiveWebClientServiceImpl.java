package com.chandradip.weatherforecastapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class ReactiveWebClientServiceImpl implements ReactiveWebClientService {

    @Autowired
    private WebClient webClient;
    @Override
    public <T> Mono<T> getForMono(String url, Class<T> tClass, HttpHeaders headers) {
        return webClient.get()
                .uri(URI.create(url))
                .headers(h->h.addAll(headers))
                .retrieve()
                .bodyToMono(tClass);
    }

    @Override
    public <T> Mono<ResponseEntity<T>> getForMonoEntity(String url, Class<T> tClass, HttpHeaders headers) {
        return webClient.get()
                .uri(URI.create(url))
                .headers(h->h.addAll(headers))
                .retrieve()
                .toEntity(tClass);
    }
}
