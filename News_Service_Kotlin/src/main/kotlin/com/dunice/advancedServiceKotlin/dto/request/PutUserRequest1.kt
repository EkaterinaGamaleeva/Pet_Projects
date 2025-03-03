package com.dunice.advancedServiceKotlin.dto.request

import com.dunice.advancedServiceKotlin.util.ServerValidationConstants
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class PutUserRequest {
    @JvmField
    var avatar: @NotBlank(message = ServerValidationConstants.USER_AVATAR_NOT_NULL) String? = null

    @JvmField
    var email: @Email(message = ServerValidationConstants.USER_EMAIL_NOT_NULL) @Size(
        min = 3,
        max = 100,
        message = ServerValidationConstants.EMAIL_SIZE_NOT_VALID
    ) @NotBlank(message = ServerValidationConstants.USER_EMAIL_NOT_NULL) String? = null

    @JvmField
    var name: @NotBlank(message = ServerValidationConstants.USER_NAME_HAS_TO_BE_PRESENT) @Size(
        min = 3,
        max = 25,
        message = ServerValidationConstants.USERNAME_SIZE_NOT_VALID
    ) String? = null

    @JvmField
    var role: @NotBlank(message = ServerValidationConstants.USER_ROLE_NULL) @Size(
        min = 3,
        max = 25,
        message = ServerValidationConstants.ROLE_SIZE_NOT_VALID
    ) String? = null
}
