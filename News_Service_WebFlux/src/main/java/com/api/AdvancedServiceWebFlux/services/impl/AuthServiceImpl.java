package com.api.AdvancedServiceWebFlux.services.impl;

import com.api.AdvancedServiceWebFlux.dto.request.AuthRequest;
import com.api.AdvancedServiceWebFlux.dto.request.RegisterUserRequest;
import com.api.AdvancedServiceWebFlux.dto.response.LoginUserResponse;
import com.api.AdvancedServiceWebFlux.exception.CustomException;
import com.api.AdvancedServiceWebFlux.mapper.UserMapper;
import com.api.AdvancedServiceWebFlux.models.User;
import com.api.AdvancedServiceWebFlux.repository.UserRepository;
import com.api.AdvancedServiceWebFlux.security.JwtUserService;
import com.api.AdvancedServiceWebFlux.services.AuthService;
import com.api.AdvancedServiceWebFlux.util.ServerErrorCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final JwtUserService jwtUserService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<LoginUserResponse> registration(RegisterUserRequest registerUserDtoRequest) {
        return userRepository.existsByEmail(registerUserDtoRequest.getEmail())
                .flatMap(exist -> {
            if (exist) {
                return Mono.error(new CustomException(ServerErrorCodes.USER_ALREADY_EXISTS));
            }
            User user = userMapper.registerUserRequestToUser(registerUserDtoRequest);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            String token = jwtUserService.generateToken(user.getEmail());
            return userRepository.save(user).map(e ->
            {
                LoginUserResponse loginUserResponse = userMapper.userToLoginUserResponse(e);
                loginUserResponse.setToken(token);
                return loginUserResponse;
            });
        });
    }

    @Override
    public Mono<LoginUserResponse> login(AuthRequest authRequest) {
        return userRepository
                .findByEmail(authRequest.getEmail())
                .switchIfEmpty(Mono.error(new CustomException(ServerErrorCodes.USER_NOT_FOUND)))
                .map(e -> {
                    e.setPassword(passwordEncoder.encode(e.getPassword()));
                    LoginUserResponse loginUserResponse = userMapper.userToLoginUserResponse(e);
                    loginUserResponse.setToken(jwtUserService.generateToken(e.getEmail()));
                    return loginUserResponse;
                });
    }
}
