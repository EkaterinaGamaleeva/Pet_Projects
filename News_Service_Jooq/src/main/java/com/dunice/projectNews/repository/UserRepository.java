package com.dunice.projectNews.repository;

import com.dunice.projectNews.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


public interface UserRepository {
    void saveUser(User user);

    User getUserById(UUID id);

    Integer checkEmail(String email);

    List<User> getAllUsers();

    void deleteByEmail(String email);

    User getUserByEmail(String email);
}
