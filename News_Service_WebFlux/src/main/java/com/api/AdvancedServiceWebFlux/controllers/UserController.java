package com.api.AdvancedServiceWebFlux.controllers;

import com.api.AdvancedServiceWebFlux.dto.common.BaseSuccessResponse;
import com.api.AdvancedServiceWebFlux.dto.common.CustomSuccessResponse;
import com.api.AdvancedServiceWebFlux.dto.request.PutUserRequest;
import com.api.AdvancedServiceWebFlux.dto.response.PublicUserResponse;
import com.api.AdvancedServiceWebFlux.services.UserService;
import com.api.AdvancedServiceWebFlux.util.AppConstants;
import com.api.AdvancedServiceWebFlux.util.ServerValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    public final UserService userService;

    @GetMapping
    public Flux<CustomSuccessResponse<PublicUserResponse>> getAllUser() {
        return userService
                .getAllUser()
                .map(CustomSuccessResponse::new);
    }

    @PutMapping
    public Mono<CustomSuccessResponse<PublicUserResponse>> replaceUser(@RequestBody
                                                                       @Valid
                                                                       PutUserRequest publicUserView) {
        return userService.replaceUser(publicUserView)
                .map(e -> new CustomSuccessResponse<>(200, e));
    }

    @DeleteMapping
    public Mono<BaseSuccessResponse> deleteUser() {
        userService.deleteUser();
        return Mono.just(new BaseSuccessResponse());
    }

    @GetMapping("/info")
    public Mono<CustomSuccessResponse<PublicUserResponse>> getUserInfo() {
        return userService
                .getUserInfo()
                .map(e -> new CustomSuccessResponse<>(1, e));
    }

    @GetMapping("/{id}")
    public Mono<CustomSuccessResponse<PublicUserResponse>> getUserInfoById(@PathVariable
                                                                                     @Size(min = 36, max = 36, message = ServerValidationConstants.MAX_UPLOAD_SIZE_EXCEEDED)
                                                                                     @Pattern(regexp = AppConstants.PATTERN_FORMAT_UUID,
                                                                                             message = ServerValidationConstants.MAX_UPLOAD_SIZE_EXCEEDED) String id) {
        return userService
                .getUserInfoById(UUID.fromString(id))
                .map(CustomSuccessResponse::new);
    }
}
