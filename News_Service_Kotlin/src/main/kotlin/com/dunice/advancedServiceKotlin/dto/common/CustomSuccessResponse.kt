package com.dunice.advancedServiceKotlin.dto.common

class CustomSuccessResponse<T> : BaseSuccessResponse {
    val data: T

    constructor(data: T) {
        this.data = data
    }

    constructor(statusCode: Int, data: T) : super(statusCode) {
        this.data = data
    }
}
