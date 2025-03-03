package com.dunice.advancedServiceKotlin.dto

import com.dunice.advancedServiceKotlin.util.ServerValidationConstants
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

open class BaseUserDto {
    @JvmField
    var avatar: @NotBlank(message = ServerValidationConstants.USER_AVATAR_NOT_NULL) String = ""

    @JvmField
    var email: @Email(message = ServerValidationConstants.USER_EMAIL_NOT_VALID) @Size(
        min = 3,
        max = 100,
        message = ServerValidationConstants.EMAIL_SIZE_NOT_VALID
    ) @NotBlank(message = ServerValidationConstants.USER_EMAIL_NOT_VALID) String=""

    @JvmField
    var name: @NotBlank(message = ServerValidationConstants.USER_NAME_HAS_TO_BE_PRESENT) @Size(
        min = 3,
        max = 25,
        message = ServerValidationConstants.USERNAME_SIZE_NOT_VALID
    ) String=""

    @JvmField
    var role: @NotBlank(message = ServerValidationConstants.USER_ROLE_NULL) @Size(
        min = 3,
        max = 25,
        message = ServerValidationConstants.ROLE_SIZE_NOT_VALID
    ) String=""

    @JvmField
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var id: String=""
}
