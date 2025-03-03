package com.dunice.projectNews.controllers;

import com.dunice.projectNews.dto.request.PutUserRequest;
import com.dunice.projectNews.dto.response.PublicUserResponse;
import com.dunice.projectNews.dto.common.BaseSuccessResponse;
import com.dunice.projectNews.dto.common.CustomSuccessResponse;
import com.dunice.projectNews.services.UserService;
import com.dunice.projectNews.util.AppConstants;
import com.dunice.projectNews.util.ServerValidationConstants;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    public final UserService userService;

    @GetMapping
    public ResponseEntity<CustomSuccessResponse<List<PublicUserResponse>>> getAllUser() {
        return ResponseEntity
                .ok()
                .body(new CustomSuccessResponse<>(userService.getAllUser()));
    }

    @PutMapping
    public ResponseEntity<CustomSuccessResponse<PublicUserResponse>> replaceUser(@RequestBody
                                                                                     @Valid
                                                                                     PutUserRequest publicUserView) {
        return ResponseEntity
                .ok()
                .body(new CustomSuccessResponse<>(200,
                        userService.replaceUser(publicUserView)));
    }

    @DeleteMapping
    public ResponseEntity<BaseSuccessResponse> deleteUser() {
        userService.deleteUser();
        return ResponseEntity
                .ok(new BaseSuccessResponse());
    }

    @GetMapping("/info")
    public ResponseEntity<CustomSuccessResponse<PublicUserResponse>> getUserInfo() {
        return ResponseEntity
                .ok()
                .body(new CustomSuccessResponse<>(1,
                        userService.getUserInfo()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<PublicUserResponse>> getUserInfoById(@PathVariable
                                                                                         @Size(min = 36, max = 36, message = ServerValidationConstants.MAX_UPLOAD_SIZE_EXCEEDED)
                                                                                         @Pattern(regexp = AppConstants.PATTERN_FORMAT_UUID,
                                                                                                 message = ServerValidationConstants.MAX_UPLOAD_SIZE_EXCEEDED) String id) {
        return ResponseEntity
                .ok()
                .body(new CustomSuccessResponse<>(1,
                        userService.getUserInfoById(UUID.fromString(id))));
    }
}
