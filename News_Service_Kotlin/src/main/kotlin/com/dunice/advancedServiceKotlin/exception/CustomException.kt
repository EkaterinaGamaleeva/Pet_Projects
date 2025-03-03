package com.dunice.advancedServiceKotlin.exception

import com.dunice.advancedServiceKotlin.util.ServerErrorCodes
import lombok.Getter

class CustomException(private val serverErrorCodes: ServerErrorCodes) : RuntimeException() {
    override val message: String = serverErrorCodes.message
}
