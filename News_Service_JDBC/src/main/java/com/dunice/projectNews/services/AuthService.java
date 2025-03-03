package com.dunice.projectNews.services;

import com.dunice.projectNews.dto.request.AuthRequest;
import com.dunice.projectNews.dto.request.RegisterUserRequest;
import com.dunice.projectNews.dto.response.LoginUserResponse;

public interface AuthService {
    LoginUserResponse registration(RegisterUserRequest registerUserDtoRequest);

    LoginUserResponse login(AuthRequest authRequest);
}
