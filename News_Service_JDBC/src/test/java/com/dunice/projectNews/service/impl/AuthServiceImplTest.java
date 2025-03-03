package com.dunice.projectNews.service.impl;

import com.dunice.projectNews.constants.TestConstants;
import com.dunice.projectNews.dto.request.AuthRequest;
import com.dunice.projectNews.dto.request.RegisterUserRequest;
import com.dunice.projectNews.dto.response.LoginUserResponse;
import com.dunice.projectNews.exception.CustomException;
import com.dunice.projectNews.mapper.UserMapper;
import com.dunice.projectNews.models.User;
import com.dunice.projectNews.repository.UserRepository;
import com.dunice.projectNews.security.JwtUserService;
import com.dunice.projectNews.services.impl.AuthServiceImpl;
import com.dunice.projectNews.util.ServerErrorCodes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        registerUserDtoRequest.setEmail(TestConstants.EMAIL);
        registerUserDtoRequest.setPassword(TestConstants.PASSWORD);
        authRequest = new AuthRequest(TestConstants.EMAIL, TestConstants.PASSWORD);
        user = new User();
        user.setUserId(TestConstants.USER_ID_UUID);
        user.setEmail(TestConstants.EMAIL);
        user.setPassword(TestConstants.PASSWORD_ENCODE);
        user.setName(TestConstants.USER_NAME);
        loginUserDtoResponse = new LoginUserResponse();
        loginUserDtoResponse.setToken(TestConstants.TOKEN);
        loginUserDtoResponse.setId(user.getUserId().toString());
        loginUserDtoResponse.setEmail(user.getEmail());

    }

    @Test
    void testRegistration_LoginUserDto_Success() {
        when(userRepository.existsByEmail(registerUserDtoRequest.getEmail())).thenReturn(false);
        when(userMapper.registerUserRequestToUser(registerUserDtoRequest)).thenReturn(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
        when(userRepository.save(user)).thenReturn(user);
        when(jwtUserService.generateToken(user.getEmail())).thenReturn(TestConstants.TOKEN);
        when(userMapper.userToLoginUserResponse(user)).thenReturn(loginUserDtoResponse);
        LoginUserResponse result = authService.registration(registerUserDtoRequest);
        assertNotNull(result);
        assertEquals(loginUserDtoResponse, result);
    }

    @Test
    void testRegistration_CustomExceptionUserAlreadyExists_NoSuccess() {
        when(userRepository.existsByEmail(registerUserDtoRequest.getEmail())).thenReturn(true);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        CustomException customException = assertThrows(CustomException.class,
                        () -> authService.registration(registerUserDtoRequest));
        assertEquals(ServerErrorCodes
                .USER_ALREADY_EXISTS
                .getMessage(),
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
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        CustomException customException = assertThrows(CustomException.class,
                        () -> authService.login(authRequest));
        assertEquals(ServerErrorCodes
                .USER_NOT_FOUND
                .getMessage(),
                customException.getMessage());
    }

    @Test
    void login_CustomExceptionUserPasswordNotValid_LoginNoSuccess() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        CustomException customException = assertThrows(CustomException.class,
                        () -> authService.login(authRequest));
        assertEquals(ServerErrorCodes
                .USER_PASSWORD_NOT_VALID
                .getMessage(),
                customException.getMessage());
    }
}
