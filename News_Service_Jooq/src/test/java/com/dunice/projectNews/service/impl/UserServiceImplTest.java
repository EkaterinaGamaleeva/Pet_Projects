package com.dunice.projectNews.service.impl;

import com.dunice.projectNews.constants.TestConstants;
import com.dunice.projectNews.dto.request.PutUserRequest;
import com.dunice.projectNews.dto.response.PublicUserResponse;
import com.dunice.projectNews.exception.CustomException;
import com.dunice.projectNews.mapper.UserMapper;
import com.dunice.projectNews.models.User;
import com.dunice.projectNews.repository.UserRepository;
import com.dunice.projectNews.services.impl.UserServiceImpl;
import com.dunice.projectNews.util.ServerErrorCodes;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        user1.setUserId(TestConstants.USER_ID_UUID);
        user1.setName(TestConstants.USER_NAME);
        user1.setEmail(TestConstants.EMAIL);
        user2 = new User();
        user2.setUserId(TestConstants.USER_ID_UUID);
        user2.setName(TestConstants.USER_NAME);
        user2.setEmail(TestConstants.EMAIL);
        usersList.add(user1);
        usersList.add(user2);
        userView1 = new PublicUserResponse();
        userView2 = new PublicUserResponse();
        userView1.setName(TestConstants.USER_NAME);
        userView1.setEmail(TestConstants.EMAIL);
        userView2.setName(TestConstants.USER_NAME);
        userView2.setEmail(TestConstants.EMAIL);
        putUserRequest = new PutUserRequest();
        putUserRequest.setEmail(TestConstants.EMAIL);
        putUserRequest.setName(TestConstants.USER_NAME);
        updatedUser = new User();
        updatedUser.setUserId(TestConstants.USER_ID_UUID);
        updatedUser.setName(TestConstants.USER_NAME);
        updatedUser.setEmail(TestConstants.EMAIL);
        updatedUser.setPassword(TestConstants.PASSWORD);
    }


    @Test
    void getAllUser_ListResponsePublicUserView_testGetAllUserSuccess() {
        List<User> usersList = Arrays.asList(user1, user2);
        List<PublicUserResponse> expectedViews =
                Arrays.asList(userView1, userView2);
        when(userRepository.getAllUsers()).thenReturn(usersList);
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
        when(authentication.getName()).thenReturn(user1.getEmail());
        when(userRepository.getUserByEmail(user1.getEmail())).thenReturn(user1);
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
        when(authentication.getName()).thenReturn(user1.getEmail());
        when(userRepository.getUserByEmail(user1.getEmail())).thenReturn(null);
        CustomException exception = assertThrows(CustomException.class,
                () -> userService.replaceUser(putUserRequest));
        assertEquals(ServerErrorCodes
                        .USER_NAME_HAS_TO_BE_PRESENT
                        .getMessage(),
                exception.getMessage());
    }

    @Test
    void deleteUser_BaseSuccessResponse_deleteUserSuccess() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user1.getEmail());
        userService.deleteUser();
        verify(userRepository,times(1)).deleteByEmail(user1.getEmail());
    }

    @Test
    void getUserInfoById_ResponsePublicUserView_getUserInfoByIdSuccess() {
        when(userRepository.getUserById(user1.getUserId())).thenReturn(user1);
        when(userMapper.userToPublicUserResponse(user1)).thenReturn(userView1);
        PublicUserResponse response = userService.getUserInfoById(user1.getUserId());
        assertNotNull(response);
        assertEquals(userView1, response);
    }

    @Test
    void getUserInfoById_CustomExceptionUserHasToBePresent_getUserInfoByIdNoSuccess() {
        when(userRepository.getUserById(user1.getUserId())).thenReturn(null);
        CustomException exception = assertThrows(CustomException.class,
                () -> userService.getUserInfoById(user1.getUserId()));
        assertEquals(ServerErrorCodes
                        .USER_NAME_HAS_TO_BE_PRESENT
                        .getMessage(),
                exception.getMessage());
    }

    @Test
    void getUserInfo_ResponsePublicUserView_getUserInfoSuccess() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user1.getEmail());
        when(userRepository.getUserByEmail(user1.getEmail())).thenReturn(user1);
        when(userMapper.userToPublicUserResponse(user1)).thenReturn(userView1);
        PublicUserResponse response = userService.getUserInfo();
        assertNotNull(response);
        assertEquals(userView1, response);
    }

    @Test
    void getUserInfo_CustomExceptionUserHasToBePresent_getUserInfoNoSuccess() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user1.getEmail());
        when(userRepository.getUserByEmail(user1.getEmail())).thenReturn(user1);
        CustomException exception = assertThrows(CustomException.class,
                () -> userService.getUserInfo());
        assertEquals(ServerErrorCodes
                        .USER_NAME_HAS_TO_BE_PRESENT
                        .getMessage(),
                exception.getMessage());
    }
}
