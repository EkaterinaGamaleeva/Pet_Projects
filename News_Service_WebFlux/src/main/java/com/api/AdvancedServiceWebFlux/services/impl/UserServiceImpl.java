package com.api.AdvancedServiceWebFlux.services.impl;


import com.api.AdvancedServiceWebFlux.dto.request.PutUserRequest;
import com.api.AdvancedServiceWebFlux.dto.response.PublicUserResponse;
import com.api.AdvancedServiceWebFlux.exception.CustomException;
import com.api.AdvancedServiceWebFlux.mapper.UserMapper;
import com.api.AdvancedServiceWebFlux.models.User;
import com.api.AdvancedServiceWebFlux.repository.UserRepository;
import com.api.AdvancedServiceWebFlux.security.CustomUserDetails;
import com.api.AdvancedServiceWebFlux.services.UserService;
import com.api.AdvancedServiceWebFlux.util.ServerErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public Flux<PublicUserResponse> getAllUser() {
        return userRepository.findAll()
                .map(userMapper::userToPublicUserResponse);
    }

    @Override
    public Mono<PublicUserResponse> replaceUser(PutUserRequest publicUserView) {
        return userRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .flatMap(u -> {
                    if (u == null) {
                        return Mono.error(new CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT));
                    }
                    User userUpdate = userMapper.putUserRequestToUser(publicUserView);
                    userUpdate.setUserId(u.getUserId());
                    userUpdate.setPassword(u.getPassword());
                    userUpdate.setNews(u.getNews());
                    return userRepository.save(userUpdate).map(userMapper::userToPublicUserResponse);
                });
    }

    @Override
    @Transactional
    public void deleteUser() {
        userRepository
                .deleteByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public Mono<PublicUserResponse> getUserInfoById(UUID id) {
        return userRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT)))
                .map(userMapper::userToPublicUserResponse);
    }

    @Override
    public Mono<PublicUserResponse> getUserInfo() {
        return getUserAuthentication()
                .switchIfEmpty(Mono.error(new CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT)))
                .map(userMapper::userToPublicUserResponse);
    }

    @Override
    public void checkingUserCurrentAuthenticationUser(UUID userId) {
        getUserAuthentication()
                .switchIfEmpty(Mono.error(new CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT)))
                .map(e -> userId.equals(e.getUserId()))
                .filter(b -> b.equals(true))
                .switchIfEmpty(Mono.error(new CustomException(ServerErrorCodes.NO_RIGHT_TO_CHANGE_NEWS)))
                .subscribe();
    }

    @Override
    public Mono<User> getUserAuthentication() {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(securityContext -> userRepository.findByEmail((String) securityContext.getAuthentication().getPrincipal()))
                .switchIfEmpty(Mono.error(new CustomException(ServerErrorCodes.USER_NOT_FOUND)));
    }
}