package com.api.AdvancedServiceWebFlux.interceptor;

import com.api.AdvancedServiceWebFlux.models.RequestLog;
import com.api.AdvancedServiceWebFlux.repository.RequestLogRepository;
import com.api.AdvancedServiceWebFlux.util.AppConstants;
import com.api.AdvancedServiceWebFlux.util.ServerErrorCodes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CustomInterceptor implements WebFilter {

    private final RequestLogRepository requestLogRepository;

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).publishOn(Schedulers.boundedElastic())
                .doAfterTerminate(() -> {
                            RequestLog log = new RequestLog(
                                    LocalDateTime.now(),
                                    exchange.getRequest().getMethod().name(),
                                    exchange.getRequest().getURI().toString(),
                                    exchange.getResponse().getStatusCode().value(),
                                    getErrorResponse(exchange),
                                    exchange.getResponse().getHeaders().get("username").get(0)
                            );
                            requestLogRepository.save(log);
                        }
                );
    }

    private String getErrorResponse(ServerWebExchange exchange) {
        if (exchange.getResponse().getStatusCode().value() != 200) {
            var a = exchange.getRequest().getHeaders().get(AppConstants.CODES).get(0);
            try {
                List<Integer> errors = objectMapper.readValue(a, List.class);
                StringBuilder errorsMessage = new StringBuilder(AppConstants.NO_SPACE);
                for (Integer error : errors) {
                    errorsMessage.append(error).append(": ").append(ServerErrorCodes.map.get(ServerErrorCodes.UNKNOWN.getMessage()))
                            .append(";");
                }
                return errorsMessage.toString();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return AppConstants.MESSAGE_STATUS_OK;
    }
}
