package com.api.AdvancedServiceWebFlux.services;


import com.api.AdvancedServiceWebFlux.dto.request.AuthRequest;
import com.api.AdvancedServiceWebFlux.dto.request.RegisterUserRequest;
import com.api.AdvancedServiceWebFlux.dto.response.LoginUserResponse;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<LoginUserResponse> registration(RegisterUserRequest registerUserDtoRequest);

    Mono<LoginUserResponse> login(AuthRequest authRequest);
}
