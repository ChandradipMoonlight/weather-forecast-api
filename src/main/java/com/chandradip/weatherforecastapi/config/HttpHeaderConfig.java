package com.chandradip.weatherforecastapi.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Map.entry;

@Component
public class HttpHeaderConfig {

    public HttpHeaders getDefaultHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        Map<String, String> map = Map.ofEntries(
                entry(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        );
//        map.forEach((k, v)->httpHeaders.set(k, v));
        map.forEach(httpHeaders::set);
        return httpHeaders;
    }

    public HttpHeaders setHeaders(Map<String, String> map) {
        HttpHeaders headers = new HttpHeaders();
        if (map!=null && !map.isEmpty()) {
            map.forEach(headers::set);
        }
        return headers;
    }
}
