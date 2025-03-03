package com.api.AdvancedServiceWebFlux.services;

import com.api.AdvancedServiceWebFlux.dto.request.PutUserRequest;
import com.api.AdvancedServiceWebFlux.dto.response.PublicUserResponse;
import com.api.AdvancedServiceWebFlux.models.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {
    Flux<PublicUserResponse> getAllUser();

    Mono<PublicUserResponse> replaceUser(PutUserRequest publicUserView);

    void deleteUser();

    Mono<PublicUserResponse> getUserInfoById(UUID id);

    Mono<PublicUserResponse> getUserInfo();

    void checkingUserCurrentAuthenticationUser(UUID userId);

    Mono<User> getUserAuthentication();
}
