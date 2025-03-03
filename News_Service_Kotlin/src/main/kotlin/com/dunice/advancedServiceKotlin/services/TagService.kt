package com.dunice.advancedServiceKotlin.services

import com.dunice.advancedServiceKotlin.dto.request.NewsRequest
import com.dunice.advancedServiceKotlin.models.Tag

interface TagService {
    fun parseTag(newsRequest: NewsRequest): Set<Tag>
}
