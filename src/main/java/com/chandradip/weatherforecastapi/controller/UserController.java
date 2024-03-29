package com.chandradip.weatherforecastapi.controller;

import com.chandradip.weatherforecastapi.constants.AppConstants;
import com.chandradip.weatherforecastapi.mappers.AppResponse;
import com.chandradip.weatherforecastapi.mappers.UserLogin;
import com.chandradip.weatherforecastapi.mappers.UserRequest;
import com.chandradip.weatherforecastapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Mono<ResponseEntity<AppResponse>> registerUser(@RequestBody UserRequest userRequest,
                                                          ServerHttpRequest httpRequest) {
        return userService.registerUser(userRequest, httpRequest)
                .map(appResponse -> ResponseEntity.ok().body(appResponse));
    }

    @GetMapping("/login")
    public Mono<ResponseEntity<AppResponse>> login(@RequestBody UserLogin userLogin) {
        return userService.loginUser(userLogin)
                .map(appResponse -> {
                    if (appResponse.getStatus().equals(AppConstants.SUCCESS)) {
                        return ResponseEntity.ok().body(appResponse);
                    } else
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(appResponse);
                });
    }

    @GetMapping("/verify/{token}")
    public Mono<ResponseEntity<AppResponse>> validateUser(@PathVariable String token) {
        log.info("Token : {}", token);
        return userService.verifyUser(token)
                .map(appResponse -> ResponseEntity.ok().body(appResponse));
    }
}
