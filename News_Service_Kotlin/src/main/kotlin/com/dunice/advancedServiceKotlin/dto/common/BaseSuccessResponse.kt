package com.dunice.advancedServiceKotlin.dto.common

import com.fasterxml.jackson.annotation.JsonInclude

open class BaseSuccessResponse(
    val statusCode: Int = 0,
    val success: Boolean = true,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var codes: List<Int?> = emptyList(),
    @JvmField
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var id: Long? = null
) {
// Custom equals and hashCode if
}