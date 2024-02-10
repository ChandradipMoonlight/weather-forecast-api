package com.chandradip.weatherforecastapi.config;

import com.chandradip.weatherforecastapi.collections.User;
import com.chandradip.weatherforecastapi.exception.InvalidOrExpiredTokenException;
import com.chandradip.weatherforecastapi.service.JWTService;
import com.chandradip.weatherforecastapi.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthManager implements ReactiveAuthenticationManager {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(auth -> auth.getCredentials().toString())
                .flatMap(token -> {
                    String userNameFromToken = jwtService.getUserNameFromToken(token);
                    if (userNameFromToken == null) {
                        // Return an error response indicating invalid token
                        return Mono.error(new InvalidOrExpiredTokenException());
                    }
                    return userService.loadUserByUsername(userNameFromToken)
                            .flatMap(user -> {
                                if (!jwtService.isTokenValid(token, user)) {
                                    return Mono.error(new InvalidOrExpiredTokenException("Token is Invalid or Expired!"));
                                }
                                return Mono.just(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities()));
                            });
                });
    }

}
