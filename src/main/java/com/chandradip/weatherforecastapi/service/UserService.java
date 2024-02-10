package com.chandradip.weatherforecastapi.service;

import com.chandradip.weatherforecastapi.collections.User;
import com.chandradip.weatherforecastapi.mappers.AppResponse;
import com.chandradip.weatherforecastapi.mappers.UserLogin;
import com.chandradip.weatherforecastapi.mappers.UserRequest;
import com.chandradip.weatherforecastapi.mappers.UserResponse;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<AppResponse> registerUser(UserRequest userRequest);
    Mono<AppResponse> verifyUser(String token);
    Mono<AppResponse> loginUser(UserLogin userLogin);
    Mono<User> loadUserByUsername(String email);
}
