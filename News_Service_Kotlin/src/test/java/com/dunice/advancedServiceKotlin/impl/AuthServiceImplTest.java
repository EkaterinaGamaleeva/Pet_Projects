package com.dunice.advancedServiceKotlin.impl;

import com.dunice.advancedServiceKotlin.constants.TestConstants;
import com.dunice.advancedServiceKotlin.dto.request.AuthRequest;
import com.dunice.advancedServiceKotlin.dto.request.RegisterUserRequest;
import com.dunice.advancedServiceKotlin.dto.response.LoginUserResponse;
import com.dunice.advancedServiceKotlin.exception.CustomException;
import com.dunice.advancedServiceKotlin.mapper.UserMapper;
import com.dunice.advancedServiceKotlin.models.User;
import com.dunice.advancedServiceKotlin.repository.UserRepository;
import com.dunice.advancedServiceKotlin.security.JwtUserService;
import com.dunice.advancedServiceKotlin.services.impl.AuthServiceImpl;
import com.dunice.advancedServiceKotlin.util.ServerErrorCodes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private JwtUserService jwtUserService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private RegisterUserRequest registerUserDtoRequest;

    private LoginUserResponse loginUserDtoResponse;

    private AuthRequest authRequest;

    private User user;

    @BeforeEach
    void setUp() {
        registerUserDtoRequest = new RegisterUserRequest();
        registerUserDtoRequest.email = TestConstants.EMAIL;
        registerUserDtoRequest.password = TestConstants.PASSWORD;
        authRequest = new AuthRequest(TestConstants.EMAIL, TestConstants.PASSWORD);
        user = new User();
        user.userId = TestConstants.USER_ID_UUID;
        user.email = TestConstants.EMAIL;
        user.password = TestConstants.PASSWORD_ENCODE;
        user.name = TestConstants.USER_NAME;
        loginUserDtoResponse = new LoginUserResponse();
        loginUserDtoResponse.token = TestConstants.TOKEN;
        loginUserDtoResponse.id = user.userId.toString();
        loginUserDtoResponse.email = user.email;

    }

    @Test
    void testRegistration_LoginUserDto_Success() {
        when(userRepository.existsByEmail(registerUserDtoRequest.email)).thenReturn(false);
        when(userMapper.registerUserRequestToUser(registerUserDtoRequest)).thenReturn(user);
        when(passwordEncoder.encode(user.password)).thenReturn(user.password);
        when(userRepository.save(user)).thenReturn(user);
        when(jwtUserService.generateToken(user.email)).thenReturn(TestConstants.TOKEN);
        when(userMapper.userToLoginUserResponse(user)).thenReturn(loginUserDtoResponse);
        LoginUserResponse result = authService.registration(registerUserDtoRequest);
        assertNotNull(result);
        assertEquals(loginUserDtoResponse, result);
    }

    @Test
    void testRegistration_CustomExceptionUserAlreadyExists_NoSuccess() {
        when(userRepository.existsByEmail(registerUserDtoRequest.email)).thenReturn(true);
        when(userRepository.existsByEmail(user.email)).thenReturn(true);
        CustomException customException = assertThrows(CustomException.class,
                        () -> authService.registration(registerUserDtoRequest));
        assertEquals(ServerErrorCodes
                .USER_ALREADY_EXISTS.message,
                customException.getMessage());
    }

    @Test
    void login_LoginUserDto_LoginSuccess() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtUserService.generateToken(anyString())).thenReturn(TestConstants.TOKEN);
        when(userMapper.userToLoginUserResponse(any(User.class))).thenReturn(loginUserDtoResponse);
        LoginUserResponse result = authService.login(authRequest);
        assertNotNull(result);
        assertEquals(loginUserDtoResponse, result);
    }

    @Test
    void login_CustomExceptionUserNotFound_LoginNoSuccess() {
        when(userRepository.findByEmail(user.email)).thenReturn(Optional.empty());
        CustomException customException = assertThrows(CustomException.class,
                        () -> authService.login(authRequest));
        assertEquals(ServerErrorCodes
                .USER_NOT_FOUND.message,
                customException.getMessage());
    }

    @Test
    void login_CustomExceptionUserPasswordNotValid_LoginNoSuccess() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        CustomException customException = assertThrows(CustomException.class,
                        () -> authService.login(authRequest));
        assertEquals(ServerErrorCodes
                .USER_PASSWORD_NOT_VALID.message,
                customException.getMessage());
    }
}
