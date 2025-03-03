package com.dunice.advancedServiceKotlin.dto.response

import java.util.*

class PublicUserResponse {
    @JvmField
    var avatar: String? = null

    @JvmField
    var email: String? = null

    @JvmField
    var name: String? = null

    @JvmField
    var role: String? = null

    @JvmField
    var id: UUID? = null
}
