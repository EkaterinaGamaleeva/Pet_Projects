package com.api.AdvancedServiceWebFlux.errors;


import com.api.AdvancedServiceWebFlux.dto.common.BaseSuccessResponse;
import com.api.AdvancedServiceWebFlux.dto.common.CustomSuccessResponse;
import com.api.AdvancedServiceWebFlux.exception.CustomException;
import com.api.AdvancedServiceWebFlux.util.AppConstants;
import com.api.AdvancedServiceWebFlux.util.ServerErrorCodes;
import com.api.AdvancedServiceWebFlux.util.ServerValidationConstants;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.MissingRequestValueException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.IntStream;

@ControllerAdvice
public class HandlerExeption extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public Mono<ResponseEntity<BaseSuccessResponse>> handleExeption(CustomException e, ServerWebExchange exchange) {
        exchange.getRequest().getHeaders().add(AppConstants.ERRORS,e.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseSuccessResponse(ServerErrorCodes.map.get(e.getMessage()),true)));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleMethodNotAllowedException(
            MethodNotAllowedException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        exchange.getRequest().getHeaders().add(AppConstants.ERRORS,ex.getMessage());
        BaseSuccessResponse baseSuccessResponse = new BaseSuccessResponse();
        baseSuccessResponse.setCodes(List.of(ServerErrorCodes.map.get(ServerErrorCodes.UNAUTHORISED.getMessage())));
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(baseSuccessResponse));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleMissingRequestValueException(
            MissingRequestValueException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        exchange.getRequest().getHeaders().add(AppConstants.ERRORS,ex.getMessage());
        BaseSuccessResponse baseSuccessResponse = new BaseSuccessResponse();
        baseSuccessResponse.setCodes(List.of(ServerErrorCodes.map.get(ServerErrorCodes.REQUEST_IS_NOT_MULTIPART.getMessage())));
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(baseSuccessResponse));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleWebExchangeBindException(
            WebExchangeBindException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        List<Integer> codes = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .flatMapToInt(code ->
                        IntStream.of(ServerErrorCodes.map.get(code)))
                .boxed()
                .toList();
        exchange.getRequest().getHeaders().add(AppConstants.ERRORS,ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CustomSuccessResponse(codes.get(0), codes)));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleServerWebInputException(
            ServerWebInputException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        exchange.getRequest().getHeaders().add(AppConstants.ERRORS,ex.getMessage());
                BaseSuccessResponse baseSuccessResponse = new BaseSuccessResponse();
        baseSuccessResponse.setCodes(List.of(ServerErrorCodes.map.get(ex.getMessage())));
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(baseSuccessResponse));
    }
}