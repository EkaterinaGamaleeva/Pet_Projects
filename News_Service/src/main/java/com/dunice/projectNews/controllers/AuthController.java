package com.dunice.projectNews.controllers;

import com.dunice.projectNews.dto.common.CustomSuccessResponse;
import com.dunice.projectNews.dto.request.AuthRequest;
import com.dunice.projectNews.dto.request.RegisterUserRequest;
import com.dunice.projectNews.dto.response.LoginUserResponse;
import com.dunice.projectNews.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<CustomSuccessResponse<LoginUserResponse>> registerUser(@RequestBody
                                                                                 @Valid
                                                                                 RegisterUserRequest
                                                                                             registerUserDtoRequest) {
        return ResponseEntity
                .ok(new CustomSuccessResponse<>(authService.registration(registerUserDtoRequest)));
    }

    @PostMapping("/login")
    public ResponseEntity<CustomSuccessResponse<LoginUserResponse>> login(@RequestBody
                                                                          @Valid
                                                                          AuthRequest authRequest) {
        return ResponseEntity
                .ok(new CustomSuccessResponse<>(authService.login(authRequest)));
    }
}
