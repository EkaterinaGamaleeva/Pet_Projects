package com.dunice.advancedServiceKotlin.services

import com.dunice.advancedServiceKotlin.dto.request.NewsRequest
import com.dunice.advancedServiceKotlin.dto.response.GetNewsOutResponse
import com.dunice.advancedServiceKotlin.dto.response.PageableResponse
import java.util.*

interface NewsService {
    fun getNews(page: Int, perPage: Int?): PageableResponse<List<GetNewsOutResponse>>

    fun createNews(newsRequest: NewsRequest): Long

    fun putNewsById(id: Long, newsRequest: NewsRequest)

    fun deleteNewsById(id: Long)

    fun getNewsFind(
        page: Int,
        perPage: Int,
        author: String,
        keywords: String,
        tags: Set<String>
    ): PageableResponse<List<GetNewsOutResponse>>

    fun getUserNews(id: UUID, page: Int, perPage: Int): PageableResponse<List<GetNewsOutResponse>>
}
