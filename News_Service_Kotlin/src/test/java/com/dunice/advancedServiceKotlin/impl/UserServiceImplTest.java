package com.dunice.advancedServiceKotlin.impl;

import com.dunice.advancedServiceKotlin.constants.TestConstants;
import com.dunice.advancedServiceKotlin.dto.request.PutUserRequest;
import com.dunice.advancedServiceKotlin.dto.response.PublicUserResponse;
import com.dunice.advancedServiceKotlin.exception.CustomException;
import com.dunice.advancedServiceKotlin.mapper.UserMapper;
import com.dunice.advancedServiceKotlin.models.User;
import com.dunice.advancedServiceKotlin.repository.UserRepository;
import com.dunice.advancedServiceKotlin.services.impl.UserServiceImpl;
import com.dunice.advancedServiceKotlin.util.ServerErrorCodes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    public UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private User updatedUser;

    private List<User> usersList;

    private PublicUserResponse userView1;

    private PublicUserResponse userView2;

    private User user1;

    private User user2;

    private PutUserRequest putUserRequest;

    @BeforeEach
    void setUp() {
        usersList = new ArrayList<>();
        user1 = new User();
        user1.userId = TestConstants.USER_ID_UUID;
        user1.name = TestConstants.USER_NAME;
        user1.email = TestConstants.EMAIL;
        user2 = new User();
        user2.userId = TestConstants.USER_ID_UUID;
        user2.name = TestConstants.USER_NAME;
        user2.email = TestConstants.EMAIL;
        usersList.add(user1);
        usersList.add(user2);
        userView1 = new PublicUserResponse();
        userView2 = new PublicUserResponse();
        userView1.name = TestConstants.USER_NAME;
        userView1.email = TestConstants.EMAIL;
        userView2.name = TestConstants.USER_NAME;
        userView2.email = TestConstants.EMAIL;
        putUserRequest = new PutUserRequest();
        putUserRequest.email = TestConstants.EMAIL;
        putUserRequest.name = TestConstants.USER_NAME;
        updatedUser = new User();
        updatedUser.userId = TestConstants.USER_ID_UUID;
        updatedUser.name = TestConstants.USER_NAME;
        updatedUser.email = TestConstants.EMAIL;
        updatedUser.password = TestConstants.PASSWORD;
    }


    @Test
    void getAllUser_ListResponsePublicUserView_testGetAllUserSuccess() {
        List<User> usersList = Arrays.asList(user1, user2);
        List<PublicUserResponse> expectedViews =
                Arrays.asList(userView1, userView2);
        when(userRepository.findAll()).thenReturn(usersList);
        when(userMapper.listUserToListPublicUserResponse(usersList)).thenReturn(expectedViews);
        List<PublicUserResponse> result = userService.getAllUser();
        assertNotNull(result);
        assertEquals(TestConstants.TWO, result.size());
        assertEquals(expectedViews, result);
    }

    @Test
    void replaceUser_ResponsePublicUserView_replaceUserSuccess() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user1.email);
        when(userRepository.findByEmail(user1.email)).thenReturn(Optional.of(user1));
        when(userMapper.putUserRequestToUser(putUserRequest)).thenReturn(updatedUser);
        when(userMapper.userToPublicUserResponse(updatedUser)).thenReturn(userView1);
        PublicUserResponse result = userService.replaceUser(putUserRequest);
        assertNotNull(result);
        assertEquals(userView1, result);
    }

    @Test
    void replaceUser_CustomExceptionUserHasToBePresent_replaceUserNoSuccess() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user1.email);
        when(userRepository.findByEmail(user1.email)).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class,
                () -> userService.replaceUser(putUserRequest));
        assertEquals(ServerErrorCodes
                        .USER_NAME_HAS_TO_BE_PRESENT.message,
                exception.getMessage());
    }

    @Test
    void deleteUser_BaseSuccessResponse_deleteUserSuccess() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user1.email);
        userService.deleteUser();
        verify(userRepository,times(1)).deleteByEmail(user1.email);
    }

    @Test
    void getUserInfoById_ResponsePublicUserView_getUserInfoByIdSuccess() {
        when(userRepository.findById(user1.userId)).thenReturn(Optional.of(user1));
        when(userMapper.userToPublicUserResponse(user1)).thenReturn(userView1);
        PublicUserResponse response = userService.getUserInfoById(user1.userId);
        assertNotNull(response);
        assertEquals(userView1, response);
    }

    @Test
    void getUserInfoById_CustomExceptionUserHasToBePresent_getUserInfoByIdNoSuccess() {
        when(userRepository.findById(user1.userId)).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class,
                () -> userService.getUserInfoById(user1.userId));
        assertEquals(ServerErrorCodes
                        .USER_NAME_HAS_TO_BE_PRESENT.message,
                exception.getMessage());
    }

    @Test
    void getUserInfo_ResponsePublicUserView_getUserInfoSuccess() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user1.email);
        when(userRepository.findByEmail(user1.email)).thenReturn(Optional.of(user1));
        when(userMapper.userToPublicUserResponse(user1)).thenReturn(userView1);
        PublicUserResponse response = userService.getUserInfo();
        assertNotNull(response);
        assertEquals(userView1, response);
    }

    @Test
    void getUserInfo_CustomExceptionUserHasToBePresent_getUserInfoNoSuccess() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user1.email);
        when(userRepository.findByEmail(user1.email)).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class,
                () -> userService.getUserInfo());
        assertEquals(ServerErrorCodes
                        .USER_NAME_HAS_TO_BE_PRESENT.message,
                exception.getMessage());
    }
}
