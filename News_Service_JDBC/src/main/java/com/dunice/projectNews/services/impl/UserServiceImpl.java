package com.dunice.projectNews.services.impl;

import com.dunice.projectNews.dto.request.PutUserRequest;
import com.dunice.projectNews.dto.response.PublicUserResponse;
import com.dunice.projectNews.dto.common.BaseSuccessResponse;
import com.dunice.projectNews.exception.CustomException;
import com.dunice.projectNews.mapper.UserMapper;
import com.dunice.projectNews.models.News;
import com.dunice.projectNews.models.User;
import com.dunice.projectNews.repository.UserRepositoryJDBC;
import com.dunice.projectNews.services.UserService;
import com.dunice.projectNews.util.ServerErrorCodes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public final UserRepositoryJDBC userRepository;

    private final UserMapper userMapper;

    @Override
    public List<PublicUserResponse> getAllUser() {
        return userMapper.listUserToListPublicUserResponse(userRepository.getAllUser());
    }

    @Override
    public PublicUserResponse replaceUser(PutUserRequest publicUserView) {
        User user = userRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT));
        User userUpdate = userMapper.putUserRequestToUser(publicUserView);
        userUpdate.setUserId(user.getUserId());
        userUpdate.setPassword(user.getPassword());
        userUpdate.setNews(user.getNews());
        userRepository.saveUser(userUpdate);
        return userMapper.userToPublicUserResponse(userUpdate);
    }

    @Override
    @Transactional
    public void deleteUser() {
        userRepository.deleteUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public PublicUserResponse getUserInfoById(UUID id) {
        return userMapper
                .userToPublicUserResponse(userRepository
                        .findById(id)
                        .orElseThrow(() -> new CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT)));
    }

    @Override
    public PublicUserResponse getUserInfo() {
        return userMapper
                .userToPublicUserResponse(userRepository
                        .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                        .orElseThrow(() -> new CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT)));
    }

    public void saveUserForNews(News news) {
        User user =
                userRepository
                        .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                        .orElseThrow(() -> new CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT));
        news.setAuthor(user);
    }

    public Boolean checkingUserCurrentAuthenticationUser(User user) {
        User userAuthentication =
                userRepository
                        .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                        .orElseThrow(() -> new CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT));
        return user.getEmail().equals(userAuthentication.getEmail());
    }
}