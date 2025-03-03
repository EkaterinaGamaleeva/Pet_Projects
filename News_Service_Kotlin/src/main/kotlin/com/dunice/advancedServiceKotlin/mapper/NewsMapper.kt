package com.dunice.advancedServiceKotlin.mapper

import News
import com.dunice.advancedServiceKotlin.dto.request.NewsRequest
import com.dunice.advancedServiceKotlin.dto.response.GetNewsOutResponse
import org.mapstruct.*
import java.util.*

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface NewsMapper {

    @Mapping(target = "tags", ignore = true)
    fun newsRequestToNews(newsRequest: NewsRequest): News

    @Mapping(target = "id", source = "newsId")
    @Mapping(target = "userId", source = "author.userId", qualifiedByName = ["uuidToString"])
    @Mapping(target = "username", source = "author.email")
    fun newsToGetNewsDtoOut(news: News): GetNewsOutResponse

    fun listNewsToListGetNewsDtoOut(news: List<News>): List<GetNewsOutResponse>

    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "newsId", ignore = true)
    @Mapping(target = "author", ignore = true)
    fun newsRequestupdateNews(newsRequest: NewsRequest, @MappingTarget news: News): News

    @Named("uuidToString")
    fun uuidToString(uuid: UUID?): String? {
        return uuid?.toString()
    }
}

