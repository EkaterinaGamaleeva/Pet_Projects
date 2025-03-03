package com.dunice.advancedServiceKotlin.dto.request

import com.dunice.advancedServiceKotlin.util.AppConstants
import com.dunice.advancedServiceKotlin.util.ServerValidationConstants
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

class AuthRequest {
    var email: @Pattern(
        regexp = AppConstants.PATTERN_FORMAT_EMAIL,
        message = ServerValidationConstants.USER_EMAIL_NOT_VALID
    ) @Size(
        min = 3,
        max = 100,
        message = ServerValidationConstants.EMAIL_SIZE_NOT_VALID
    ) @NotBlank(message = ServerValidationConstants.USER_EMAIL_NOT_VALID) String=""

    var password: @NotBlank(message = ServerValidationConstants.PASSWORD_NOT_VALID) String? = null

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }

    constructor()
}
