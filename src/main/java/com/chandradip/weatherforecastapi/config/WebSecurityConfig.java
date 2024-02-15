package com.chandradip.weatherforecastapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

    public static final String[] WHITE_LIST_URLS = {
            "/api/v1/user/register",
            "/api/v1/user/verify/**",
            "/api/v1/user/login",
            "/v3/api-docs",
            "/webjars/**",
            "/swagger-ui/**",  // Whitelist Swagger UI
            "/swagger-ui.html",
            "/swagger-resources/**"
    };


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity, AuthManager authManager, SecurityContextRepository contextRepository) {
        ServerHttpSecurity serverHttpSecurity = httpSecurity
                .cors(corsSpec -> corsSpec.disable())
                .csrf(csrfSpec -> csrfSpec.disable())
                .authorizeExchange(auth -> {
                    auth.pathMatchers(WHITE_LIST_URLS).permitAll()
                            .anyExchange().authenticated();
                        }
                )
                .exceptionHandling(exceptions ->
                        exceptions
                                .authenticationEntryPoint((swe, e) ->
                                        Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                                .accessDeniedHandler((swe, e) ->
                                        Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
                )
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(fl->fl.disable())
                .authenticationManager(authManager)
                .securityContextRepository(contextRepository)
                ;

        return serverHttpSecurity.build();
    }

}
