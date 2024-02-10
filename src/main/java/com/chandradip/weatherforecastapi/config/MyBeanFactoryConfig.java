package com.chandradip.weatherforecastapi.config;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class MyBeanFactoryConfig {


//    @Bean
//    public WebClient.Builder webClientBuilder() {
//        return WebClient.builder();
//    }
//
//    @Bean
//    public WebClient webClient(WebClient.Builder webClientBuilder) {
//        return webClientBuilder.build();
//    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }
}
