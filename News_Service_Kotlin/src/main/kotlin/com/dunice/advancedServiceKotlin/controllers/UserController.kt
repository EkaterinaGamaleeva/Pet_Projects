package com.dunice.advancedServiceKotlin.controllers

import com.dunice.advancedServiceKotlin.dto.common.BaseSuccessResponse
import com.dunice.advancedServiceKotlin.dto.common.CustomSuccessResponse
import com.dunice.advancedServiceKotlin.dto.request.PutUserRequest
import com.dunice.advancedServiceKotlin.dto.response.PublicUserResponse
import com.dunice.advancedServiceKotlin.services.UserService
import com.dunice.advancedServiceKotlin.util.AppConstants
import com.dunice.advancedServiceKotlin.util.ServerValidationConstants
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
@Validated
class UserController @Autowired constructor(val userService: UserService) {
    @get:GetMapping
    val allUser: ResponseEntity<CustomSuccessResponse<List<PublicUserResponse?>?>>
        get() = ResponseEntity
            .ok()
            .body(
                CustomSuccessResponse(
                    userService.allUser
                )
            )

    @PutMapping
    fun replaceUser(@RequestBody publicUserView: @Valid PutUserRequest?): ResponseEntity<CustomSuccessResponse<PublicUserResponse?>> {
        return ResponseEntity
            .ok()
            .body(
                CustomSuccessResponse(
                    200,
                    userService.replaceUser(publicUserView)
                )
            )
    }

    @DeleteMapping
    fun deleteUser(): ResponseEntity<BaseSuccessResponse> {
        userService.deleteUser()
        return ResponseEntity
            .ok(BaseSuccessResponse())
    }

    @get:GetMapping("/info")
    val userInfo: ResponseEntity<CustomSuccessResponse<PublicUserResponse?>>
        get() = ResponseEntity
            .ok()
            .body(
                CustomSuccessResponse(
                    1,
                    userService.userInfo
                )
            )

    @GetMapping("/{id}")
    fun getUserInfoById(
        @PathVariable id: @Size(
            min = 36,
            max = 36,
            message = ServerValidationConstants.MAX_UPLOAD_SIZE_EXCEEDED
        ) @Pattern(
            regexp = AppConstants.PATTERN_FORMAT_UUID,
            message = ServerValidationConstants.MAX_UPLOAD_SIZE_EXCEEDED
        ) String?
    ): ResponseEntity<CustomSuccessResponse<PublicUserResponse?>> {
        return ResponseEntity
            .ok()
            .body(
                CustomSuccessResponse(
                    1,
                    userService.getUserInfoById(UUID.fromString(id))
                )
            )
    }
}
