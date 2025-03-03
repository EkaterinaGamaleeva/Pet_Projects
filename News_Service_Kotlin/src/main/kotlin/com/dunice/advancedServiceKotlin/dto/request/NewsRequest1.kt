package com.dunice.advancedServiceKotlin.dto.request

import com.dunice.advancedServiceKotlin.util.ServerValidationConstants
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class NewsRequest {
    @JvmField
    var description: @NotBlank(message = ServerValidationConstants.NEWS_DESCRIPTION_HAS_TO_BE_PRESENT) @Size(
        min = 3,
        max = 160,
        message = ServerValidationConstants.NEWS_DESCRIPTION_SIZE_NOT_VALID
    ) String? = null

    @JvmField
    var image: @NotBlank(message = ServerValidationConstants.NEWS_IMAGE_HAS_TO_BE_PRESENT) @Size(
        min = 3,
        max = 130,
        message = ServerValidationConstants.NEWS_IMAGE_HAS_TO_BE_PRESENT
    ) String? = null

    var tags: Set<String>? = null

    @JvmField
    var title: @NotBlank(message = ServerValidationConstants.NEWS_TITLE_NOT_NULL) @Size(
        min = 3,
        max = 160,
        message = ServerValidationConstants.NEWS_TITLE_SIZE
    ) String? = null
}
