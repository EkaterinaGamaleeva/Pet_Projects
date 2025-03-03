package com.api.AdvancedServiceWebFlux.controllers;

import com.api.AdvancedServiceWebFlux.dto.common.CustomSuccessResponse;
import com.api.AdvancedServiceWebFlux.dto.request.AuthRequest;
import com.api.AdvancedServiceWebFlux.dto.request.RegisterUserRequest;
import com.api.AdvancedServiceWebFlux.dto.response.LoginUserResponse;
import com.api.AdvancedServiceWebFlux.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public Mono<CustomSuccessResponse<LoginUserResponse>> registerUser(@RequestBody
                                                                                 @Valid
                                                                           RegisterUserRequest
                                                                                             registerUserDtoRequest) {
        return authService.registration(registerUserDtoRequest).map(CustomSuccessResponse::new);
    }

    @PostMapping("/login")
    public Mono<CustomSuccessResponse<LoginUserResponse>> login(@RequestBody
                                                                          @Valid
                                                                AuthRequest authRequest) {
        return authService.login(authRequest).map(CustomSuccessResponse::new);
    }
}
