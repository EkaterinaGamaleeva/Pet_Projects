package com.dunice.advancedServiceKotlin.controllers

import com.dunice.advancedServiceKotlin.dto.common.CustomSuccessResponse
import com.dunice.advancedServiceKotlin.dto.request.AuthRequest
import com.dunice.advancedServiceKotlin.dto.request.RegisterUserRequest
import com.dunice.advancedServiceKotlin.dto.response.LoginUserResponse
import com.dunice.advancedServiceKotlin.services.AuthService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(private val authService: AuthService) {
    @PostMapping("/register")
    fun registerUser(@RequestBody registerUserDtoRequest: @Valid RegisterUserRequest?): ResponseEntity<CustomSuccessResponse<LoginUserResponse>> {
        return ResponseEntity
            .ok(
                CustomSuccessResponse(
                    authService.registration(
                        registerUserDtoRequest!!
                    )
                )
            )
    }

    @PostMapping("/login")
    fun login(@RequestBody authRequest: @Valid AuthRequest?): ResponseEntity<CustomSuccessResponse<LoginUserResponse>> {
        return ResponseEntity
            .ok(
                CustomSuccessResponse(
                    authService.login(
                        authRequest!!
                    )
                )
            )
    }
}
