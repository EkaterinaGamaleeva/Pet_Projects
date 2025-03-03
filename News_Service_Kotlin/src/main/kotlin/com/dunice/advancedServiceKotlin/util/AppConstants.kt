package com.dunice.advancedServiceKotlin.util

interface AppConstants {
    companion object {
        const val SPACE: String = " "

        const val NO_SPACE: String = ""

        const val DOT: String = "."

        const val HEADER_AUTHORIZATION: String = "Authorization"

        const val TOKEN_BEARER: String = "Bearer" + SPACE

        const val TYPE_APPLICATION_JSON: String = "application/json"

        const val ERRORS: String = "errorMessage"

        const val MESSAGE_STATUS_OK: String = "Все окей"

        const val NO_AUTHORIZATION: String = "Не авторизованный пользователь"

        const val PATTERN_FORMAT_EMAIL: String = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+[A-Za-z]{2,6}$"

        const val PATTERN_FORMAT_UUID: String =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"
    }
}
