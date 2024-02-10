package com.chandradip.weatherforecastapi.config;

import com.chandradip.weatherforecastapi.constants.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
@Slf4j
public class SecurityContextRepository implements ServerSecurityContextRepository {
    @Autowired
    private AuthManager authenticationManage;
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw  new UnsupportedOperationException("This save operation is not supported yet");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(AppConstants.AUTHORIZATION))
                .filter(token-> token.startsWith("Bearer "))
                .map(token->token.substring(7))
                .flatMap(token-> {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(token, token);
                    return this.authenticationManage.authenticate(authentication).map(SecurityContextImpl::new);
                });
    }
}
