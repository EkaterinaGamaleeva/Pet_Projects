package com.dunice.advancedServiceKotlin.services.impl

import com.dunice.advancedServiceKotlin.dto.request.AuthRequest
import com.dunice.advancedServiceKotlin.dto.request.RegisterUserRequest
import com.dunice.advancedServiceKotlin.dto.response.LoginUserResponse
import com.dunice.advancedServiceKotlin.exception.CustomException
import com.dunice.advancedServiceKotlin.mapper.UserMapper
import com.dunice.advancedServiceKotlin.repository.UserRepository
import com.dunice.advancedServiceKotlin.security.JwtUserService
import com.dunice.advancedServiceKotlin.services.AuthService
import com.dunice.advancedServiceKotlin.util.ServerErrorCodes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl @Autowired constructor(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val jwtUserService: JwtUserService,
    private val passwordEncoder: PasswordEncoder
) : AuthService {
    override fun registration(registerUserDtoRequest: RegisterUserRequest): LoginUserResponse {
        if (userRepository.existsByEmail(registerUserDtoRequest.email)) {
            throw CustomException(ServerErrorCodes.USER_ALREADY_EXISTS)
        }
        val user = userMapper.registerUserRequestToUser(registerUserDtoRequest)
        user.password = passwordEncoder.encode(user.password)
        userRepository.save(user)
        val token = jwtUserService.generateToken(user.email)
        val loginUserDtoResponse = userMapper.userToLoginUserResponse(user)
        loginUserDtoResponse.token = token
        return loginUserDtoResponse
    }

    override fun login(authRequest: AuthRequest): LoginUserResponse {
        val user = userRepository.findByEmail(authRequest.email)
            ?: throw CustomException(ServerErrorCodes.USER_NOT_FOUND)
        if (!passwordEncoder
                .matches(authRequest.password, user.password)
        ) {
            throw CustomException(ServerErrorCodes.USER_PASSWORD_NOT_VALID)
        }
        val token = jwtUserService.generateToken(authRequest.email)
        val loginUserDtoResponse = userMapper.userToLoginUserResponse(user)
        loginUserDtoResponse.token = token
        return loginUserDtoResponse
    }
}
