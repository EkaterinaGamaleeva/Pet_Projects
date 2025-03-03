package com.dunice.advancedServiceKotlin.controllers

import com.dunice.advancedServiceKotlin.dto.common.BaseSuccessResponse
import com.dunice.advancedServiceKotlin.dto.common.CustomSuccessResponse
import com.dunice.advancedServiceKotlin.dto.request.NewsRequest
import com.dunice.advancedServiceKotlin.dto.response.GetNewsOutResponse
import com.dunice.advancedServiceKotlin.dto.response.PageableResponse
import com.dunice.advancedServiceKotlin.services.NewsService
import com.dunice.advancedServiceKotlin.util.ServerValidationConstants
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/news")
@Validated
class NewsController @Autowired constructor(private val newsService: NewsService) {
    @GetMapping
    fun getNews(
        @RequestParam(value = "page") page: @NotNull(message = ServerValidationConstants.PARAM_PAGE_NOT_NULL) @Min(
            value = 1,
            message = ServerValidationConstants.PAGE_SIZE_NOT_VALID
        ) Int?,
        @RequestParam(value = "perPage") perPage: @NotNull(message = ServerValidationConstants.PARAM_PER_PAGE_NOT_NULL) @Min(
            value = 1,
            message = ServerValidationConstants.PER_PAGE_MIN_NOT_VALID
        ) @Max(value = 100, message = ServerValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100) Int?
    ): ResponseEntity<CustomSuccessResponse<PageableResponse<List<GetNewsOutResponse>>>> {
        return ResponseEntity
            .ok()
            .body(
                CustomSuccessResponse(
                    newsService.getNews(page!!, perPage)
                )
            )
    }

    @PostMapping
    fun createNews(@RequestBody newsRequest: @Valid NewsRequest?): ResponseEntity<BaseSuccessResponse> {
        val baseSuccessResponse = BaseSuccessResponse()
        baseSuccessResponse.id = newsService.createNews(newsRequest!!)
        return ResponseEntity
            .ok()
            .body(baseSuccessResponse)
    }

    @PutMapping("/{id}")
    fun putNewsById(
        @PathVariable("id") id: @Positive(message = ServerValidationConstants.ID_MUST_BE_POSITIVE) Long?,
        @RequestBody newsRequest: @Valid NewsRequest?
    ): ResponseEntity<BaseSuccessResponse> {
        newsService.putNewsById(id!!, newsRequest!!)
        return ResponseEntity
            .ok()
            .body(BaseSuccessResponse())
    }

    @DeleteMapping("/{id}")
    fun deleteNewsById(@PathVariable("id") id: @Positive(message = ServerValidationConstants.ID_MUST_BE_POSITIVE) Long?): ResponseEntity<BaseSuccessResponse> {
        newsService.deleteNewsById(id!!)
        return ResponseEntity
            .ok()
            .body(BaseSuccessResponse())
    }

    @GetMapping("/find")
    fun getNewsFind(
        @RequestParam(value = "page") page: @NotNull(message = ServerValidationConstants.PARAM_PAGE_NOT_NULL) @Min(
            value = 1,
            message = ServerValidationConstants.PAGE_SIZE_NOT_VALID
        ) Int,
        @RequestParam(value = "perPage") perPage: @NotNull(message = ServerValidationConstants.PARAM_PER_PAGE_NOT_NULL) @Min(
            value = 1,
            message = ServerValidationConstants.PER_PAGE_MIN_NOT_VALID
        ) @Max(value = 100, message = ServerValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100) Int,
        @RequestParam(value = "author", required = false) author: String,
        @RequestParam(value = "keywords", required = false) keywords: String,
        @RequestParam(value = "tags", required = false) tags: Set<String>
    ): ResponseEntity<CustomSuccessResponse<PageableResponse<List<GetNewsOutResponse>>>> {
        return ResponseEntity
            .ok()
            .body(
                CustomSuccessResponse(
                    newsService
                        .getNewsFind(
                            page!!,
                            perPage,
                            author,
                            keywords,
                            tags
                        )
                )
            )
    }

    @GetMapping("user/{userId}")
    fun getUserNews(
        @PathVariable("userId") id: @Positive(message = ServerValidationConstants.ID_MUST_BE_POSITIVE) UUID,
        @RequestParam(value = "page") page: @NotNull(message = ServerValidationConstants.PARAM_PAGE_NOT_NULL) @Min(
            value = 1,
            message = ServerValidationConstants.PAGE_SIZE_NOT_VALID
        ) Int,
        @RequestParam(value = "perPage") perPage: @NotNull(message = ServerValidationConstants.PARAM_PER_PAGE_NOT_NULL) @Min(
            value = 1,
            message = ServerValidationConstants.PER_PAGE_MIN_NOT_VALID
        ) @Max(value = 100, message = ServerValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100) Int
    ): ResponseEntity<CustomSuccessResponse<PageableResponse<List<GetNewsOutResponse>>>> {
        return ResponseEntity
            .ok()
            .body(
                CustomSuccessResponse(
                    newsService
                        .getUserNews(
                            id,
                            page!!,
                            perPage
                        )
                )
            )
    }
}
