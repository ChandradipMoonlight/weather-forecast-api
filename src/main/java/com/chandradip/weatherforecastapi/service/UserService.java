package com.chandradip.weatherforecastapi.service;

import com.chandradip.weatherforecastapi.collections.User;
import com.chandradip.weatherforecastapi.mappers.AppResponse;
import com.chandradip.weatherforecastapi.mappers.UserLogin;
import com.chandradip.weatherforecastapi.mappers.UserRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<AppResponse> registerUser(UserRequest userRequest, ServerHttpRequest httpRequest);
    Mono<AppResponse> verifyUser(String token);
    Mono<AppResponse> loginUser(UserLogin userLogin);
    Mono<User> loadUserByUsername(String email);
}
