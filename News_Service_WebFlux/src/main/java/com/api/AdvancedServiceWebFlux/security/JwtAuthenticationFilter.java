package com.api.AdvancedServiceWebFlux.security;

import com.api.AdvancedServiceWebFlux.util.AppConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUserService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        exchange.getRequest().getHeaders().put("username", List.of("Unauthorized"));
        if (exchange.getRequest().getHeaders().containsKey(AppConstants.HEADER_AUTHORIZATION)) {
            String authHeader = exchange.getRequest().getHeaders().getFirst(AppConstants.HEADER_AUTHORIZATION);
            if (!StringUtils.hasText(authHeader) && !StringUtils.startsWithIgnoreCase(authHeader, AppConstants.TOKEN_BEARER)) {
                return chain.filter(exchange);
            }

            String jwt = authHeader.substring(AppConstants.TOKEN_BEARER.length());

            if (jwtService.validateToken(jwt)) {
                String username = jwtService.getEmailByToken(jwt);
                return userDetailsService.findByUsername(username).flatMap(userDetails -> {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(),
                                    userDetails.getAuthorities());
                    exchange.getRequest().getHeaders().put("username", List.of(userDetails.getUsername()));
                    return chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(usernamePasswordAuthenticationToken));
                });
            }
        }
            return chain.filter(exchange);
        }
}