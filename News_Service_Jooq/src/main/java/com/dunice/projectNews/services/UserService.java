package com.dunice.projectNews.services;

import com.dunice.projectNews.dto.request.PutUserRequest;
import com.dunice.projectNews.dto.response.PublicUserResponse;
import com.dunice.projectNews.models.News;
import com.dunice.projectNews.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<PublicUserResponse> getAllUser();

    PublicUserResponse replaceUser(PutUserRequest publicUserView);

    void deleteUser();

    PublicUserResponse getUserInfoById(UUID id);

    PublicUserResponse getUserInfo();

    void saveUserForNews(News news);

    Boolean checkingUserCurrentAuthenticationUser(User user);
}
