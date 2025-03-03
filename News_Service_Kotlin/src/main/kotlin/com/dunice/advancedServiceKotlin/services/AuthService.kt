package com.dunice.advancedServiceKotlin.services

import com.dunice.advancedServiceKotlin.dto.request.AuthRequest
import com.dunice.advancedServiceKotlin.dto.request.RegisterUserRequest
import com.dunice.advancedServiceKotlin.dto.response.LoginUserResponse

interface AuthService {
    fun registration(registerUserDtoRequest: RegisterUserRequest): LoginUserResponse

    fun login(authRequest: AuthRequest): LoginUserResponse
}
