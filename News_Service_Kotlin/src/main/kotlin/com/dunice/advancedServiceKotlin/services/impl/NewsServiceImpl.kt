package com.dunice.advancedServiceKotlin.services.impl

import com.dunice.advancedServiceKotlin.dto.request.NewsRequest
import com.dunice.advancedServiceKotlin.dto.response.GetNewsOutResponse
import com.dunice.advancedServiceKotlin.dto.response.PageableResponse
import com.dunice.advancedServiceKotlin.exception.CustomException
import com.dunice.advancedServiceKotlin.mapper.NewsMapper
import com.dunice.advancedServiceKotlin.repository.NewsRepository
import com.dunice.advancedServiceKotlin.services.NewsService
import com.dunice.advancedServiceKotlin.services.TagService
import com.dunice.advancedServiceKotlin.services.UserService
import com.dunice.advancedServiceKotlin.util.ServerErrorCodes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class NewsServiceImpl @Autowired constructor(
    private val newsRepository: NewsRepository,
    private val newsMapper: NewsMapper,
    private val userService: UserService,
    private val tagService: TagService
) : NewsService {
    override fun getNews(page: Int, perPage: Int?): PageableResponse<List<GetNewsOutResponse>> {
        val pageable: Pageable = PageRequest.of(page - 1, perPage!!)

        val getNews = newsMapper
            .listNewsToListGetNewsDtoOut(
                newsRepository
                    .findAll(pageable)
                    .content
                    .filterNotNull()
            )

        return PageableResponse(getNews, getNews.size)
    }

    @Transactional
    override fun createNews(newsRequest: NewsRequest): Long {
        val news = newsMapper.newsRequestToNews(newsRequest)
        userService.saveUserForNews(news)
        news.tags = tagService.parseTag(newsRequest)
        newsRepository.save(news)
        return news.newsId
    }

    @Transactional
    override fun putNewsById(id: Long, newsRequest: NewsRequest) {
        val getNewsEntity = newsRepository
            .findById(id)
            .orElseThrow { CustomException(ServerErrorCodes.NEWS_NOT_FOUND) }
            ?: throw CustomException(ServerErrorCodes.NEWS_NOT_FOUND)
        if (userService.checkingUserCurrentAuthenticationUser(getNewsEntity.author)) {
            var news = newsMapper.newsRequestupdateNews(newsRequest, getNewsEntity)
            news.newsId =getNewsEntity.newsId
            news.author = getNewsEntity.author
            news.tags = tagService.parseTag(newsRequest)
            newsRepository.save(news)
        } else {
            throw CustomException(ServerErrorCodes.NO_RIGHT_TO_CHANGE_NEWS)
        }
    }

    override fun deleteNewsById(id: Long) {
        val news = newsRepository.findById(id).orElseThrow { CustomException(ServerErrorCodes.NEWS_NOT_FOUND) }
            ?: throw CustomException(ServerErrorCodes.NEWS_NOT_FOUND)
        if (userService
                .checkingUserCurrentAuthenticationUser(news.author)
        ) {
            newsRepository.deleteById(id)
        } else {
            throw CustomException(ServerErrorCodes.NO_RIGHT_TO_CHANGE_NEWS)
        }
    }

    override fun getNewsFind(
        page: Int,
        perPage: Int,
        author: String,
        keywords: String,
        tags: Set<String>
    ): PageableResponse<List<GetNewsOutResponse>> {
        val pageable: Pageable = PageRequest.of(page - 1, perPage!!)
        val news = newsRepository
            .findAllNewsDinamicWhere(
                author,
                keywords,
                tags, pageable
            ) ?: throw CustomException(ServerErrorCodes.NEWS_NOT_FOUND)
        val getNews = newsMapper
            .listNewsToListGetNewsDtoOut(
                news
                    .content
                    .filterNotNull()
            )

        val pageableResponse = PageableResponse(getNews)

        pageableResponse.numberOfElements = getNews.size
        return pageableResponse
    }

    override fun getUserNews(id: UUID, page: Int, perPage: Int): PageableResponse<List<GetNewsOutResponse>> {
        val pageable: Pageable = PageRequest.of(page - 1, perPage!!)
        val news = newsRepository
            .findAllNewsWithDetailsByUserId(id, pageable) ?: throw CustomException(ServerErrorCodes.NEWS_NOT_FOUND)
        val getNewsOutResponseList = newsMapper
            .listNewsToListGetNewsDtoOut(
                news
                    .content
                    .filterNotNull()
            )

        val pageableResponse =
            PageableResponse(getNewsOutResponseList)

        pageableResponse.numberOfElements = getNewsOutResponseList.size

        return pageableResponse
    }
}
