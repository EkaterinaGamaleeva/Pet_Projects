package com.dunice.advancedServiceKotlin.dto.request

import com.dunice.advancedServiceKotlin.dto.BaseUserDto
import com.dunice.advancedServiceKotlin.util.ServerValidationConstants
import jakarta.validation.constraints.NotBlank

class RegisterUserRequest : BaseUserDto {
    @JvmField
    var password: @NotBlank(message = ServerValidationConstants.USER_PASSWORD_NULL) String =""

    constructor(password: String) {
        this.password = password
    }

    constructor()
}
