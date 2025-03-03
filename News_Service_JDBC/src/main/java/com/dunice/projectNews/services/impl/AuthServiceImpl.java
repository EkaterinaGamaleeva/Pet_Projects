package com.dunice.projectNews.services.impl;

import com.dunice.projectNews.dto.request.AuthRequest;
import com.dunice.projectNews.dto.request.RegisterUserRequest;
import com.dunice.projectNews.dto.response.LoginUserResponse;
import com.dunice.projectNews.exception.CustomException;
import com.dunice.projectNews.mapper.UserMapper;
import com.dunice.projectNews.models.User;
import com.dunice.projectNews.repository.UserRepositoryJDBC;
import com.dunice.projectNews.security.JwtUserService;
import com.dunice.projectNews.services.AuthService;
import com.dunice.projectNews.util.ServerErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepositoryJDBC userRepository;

    private final UserMapper userMapper;

    private final JwtUserService jwtUserService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginUserResponse registration(RegisterUserRequest registerUserDtoRequest) {
        userRepository.checkEmail(registerUserDtoRequest.getEmail());
        User user = userMapper.registerUserRequestToUser(registerUserDtoRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveUser(user);
        String token = jwtUserService.generateToken(user.getEmail());
        LoginUserResponse loginUserDtoResponse = userMapper.userToLoginUserResponse(user);
        loginUserDtoResponse.setToken(token);
        return loginUserDtoResponse;
    }

    @Override
    public LoginUserResponse login(AuthRequest authRequest) {
        User user = userRepository
                .findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new CustomException(ServerErrorCodes.USER_NOT_FOUND));
        if (!passwordEncoder
                .matches(authRequest.getPassword(), user.getPassword())) {
            throw new CustomException(ServerErrorCodes.USER_PASSWORD_NOT_VALID);
        }
        String token = jwtUserService.generateToken(authRequest.getEmail());
        LoginUserResponse loginUserDtoResponse = userMapper.userToLoginUserResponse(user);
        loginUserDtoResponse.setToken(token);
        return loginUserDtoResponse;
    }

}