package com.dunice.advancedServiceKotlin.dto.response

import com.dunice.advancedServiceKotlin.dto.BaseUserDto

class LoginUserResponse : BaseUserDto() {
    @JvmField
    var token: String=""
}
