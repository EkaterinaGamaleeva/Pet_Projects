package com.dunice.projectNews.services.impl;

import com.dunice.projectNews.dto.request.PutUserRequest;
import com.dunice.projectNews.dto.response.PublicUserResponse;
import com.dunice.projectNews.exception.CustomException;
import com.dunice.projectNews.mapper.UserMapper;
import com.dunice.projectNews.models.News;
import com.dunice.projectNews.models.User;
import com.dunice.projectNews.repository.UserRepository;
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

    public final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public List<PublicUserResponse> getAllUser() {
        return userMapper.listUserToListPublicUserResponse(userRepository.getAllUsers());
    }

    @Override
    public PublicUserResponse replaceUser(PutUserRequest publicUserView) {
        User user = userRepository.getUserByEmail(publicUserView.getEmail());
        User userUpdate = userMapper.putUserRequestToUser(publicUserView);
        userUpdate.setUserId(user.getUserId());
        userUpdate.setPassword(user.getPassword());
        userUpdate.setNews(user.getNews());
        userRepository.deleteByEmail(user.getEmail());
        userRepository.saveUser(userUpdate);
        return userMapper.userToPublicUserResponse(userUpdate);
    }

    @Override
    @Transactional
    public void deleteUser() {
        userRepository
                .deleteByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public PublicUserResponse getUserInfoById(UUID id) {
        return userMapper
                .userToPublicUserResponse(userRepository.getUserById(id));
    }
    @Override
    public PublicUserResponse getUserInfo() {
        return userMapper
                .userToPublicUserResponse(userRepository
                        .getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    public void saveUserForNews(News news) {
        User user =
                userRepository.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        news.setAuthor(user);
    }

    public Boolean checkingUserCurrentAuthenticationUser(User user) {
        User userAuthentication =
                userRepository
                        .getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return user.getEmail().equals(userAuthentication.getEmail());
    }
}
