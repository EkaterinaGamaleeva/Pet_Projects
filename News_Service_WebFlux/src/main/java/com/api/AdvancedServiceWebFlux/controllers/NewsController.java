package com.api.AdvancedServiceWebFlux.controllers;

import com.api.AdvancedServiceWebFlux.dto.common.BaseSuccessResponse;
import com.api.AdvancedServiceWebFlux.dto.common.CustomSuccessResponse;
import com.api.AdvancedServiceWebFlux.dto.request.NewsRequest;
import com.api.AdvancedServiceWebFlux.dto.response.GetNewsOutResponse;
import com.api.AdvancedServiceWebFlux.dto.response.PageableResponse;
import com.api.AdvancedServiceWebFlux.services.NewsService;
import com.api.AdvancedServiceWebFlux.util.ServerValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/news")
@Validated
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public Mono<CustomSuccessResponse<PageableResponse<List<GetNewsOutResponse>>>> getNews(@RequestParam(value = "page")
                                                                                           @NotNull(message = ServerValidationConstants.PARAM_PAGE_NOT_NULL)
                                                                                           @Min(value = 1, message = ServerValidationConstants.PAGE_SIZE_NOT_VALID)
                                                                                           Integer page,
                                                                                           @RequestParam(value = "perPage")
                                                                                           @NotNull(message = ServerValidationConstants.PARAM_PER_PAGE_NOT_NULL)
                                                                                           @Min(value = 1, message = ServerValidationConstants.PER_PAGE_MIN_NOT_VALID)
                                                                                           @Max(value = 100, message = ServerValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100)
                                                                                           Integer perPage) {
        return newsService.getNews(page, perPage).map(CustomSuccessResponse::new);
    }

    @PostMapping
    public Mono<BaseSuccessResponse> createNews(@RequestBody @Valid NewsRequest newsRequest) {
        return newsService.createNews(newsRequest).map(l -> {
            BaseSuccessResponse baseSuccessResponse = new BaseSuccessResponse();
            baseSuccessResponse.setId(l);
            return baseSuccessResponse;
        });
    }

    @PutMapping("/{id}")
    public Mono<BaseSuccessResponse> putNewsById(@PathVariable("id")
                                                 @Positive(message =
                                                         ServerValidationConstants.ID_MUST_BE_POSITIVE) Long id,
                                                 @RequestBody @Valid NewsRequest newsRequest) {
        return newsService.putNewsById(id, newsRequest).map(e -> new BaseSuccessResponse());
    }

    @DeleteMapping("/{id}")
    public Mono<BaseSuccessResponse> deleteNewsById(@PathVariable("id")
                                                    @Positive(message =
                                                            ServerValidationConstants.ID_MUST_BE_POSITIVE) Long id) {
        return newsService
                .deleteNewsById(id)
                .map(e -> new BaseSuccessResponse());

    }

    @GetMapping("/find")
    public Mono<CustomSuccessResponse<PageableResponse<List<GetNewsOutResponse>>>> getNewsFind(@RequestParam(value = "page")
                                                                                               @NotNull(message = ServerValidationConstants.PARAM_PAGE_NOT_NULL)
                                                                                               @Min(value = 1, message = ServerValidationConstants.PAGE_SIZE_NOT_VALID)
                                                                                               Integer page,
                                                                                               @RequestParam(value = "perPage")
                                                                                               @NotNull(message = ServerValidationConstants.PARAM_PER_PAGE_NOT_NULL)
                                                                                               @Min(value = 1, message = ServerValidationConstants.PER_PAGE_MIN_NOT_VALID)
                                                                                               @Max(value = 100, message = ServerValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100)
                                                                                               Integer perPage,
                                                                                               @RequestParam(value = "author", required = false) String author,
                                                                                               @RequestParam(value = "keywords", required = false) String keywords,
                                                                                               @RequestParam(value = "tags", required = false) Set<String> tags
    ) {
        return newsService
                .getNewsFind(
                        page,
                        perPage,
                        author,
                        keywords,
                        tags).map(CustomSuccessResponse::new);
    }

    @GetMapping("/user/{userId}")
    public Mono<CustomSuccessResponse<PageableResponse<List<GetNewsOutResponse>>>> getUserNews(@PathVariable("userId") UUID id,
                                                                                               @RequestParam(value = "page")
                                                                                               @NotNull(message = ServerValidationConstants.PARAM_PAGE_NOT_NULL)
                                                                                               @Min(value = 1, message = ServerValidationConstants.PAGE_SIZE_NOT_VALID)
                                                                                               Integer page,
                                                                                               @RequestParam(value = "perPage")
                                                                                               @NotNull(message = ServerValidationConstants.PARAM_PER_PAGE_NOT_NULL)
                                                                                               @Min(value = 1, message = ServerValidationConstants.PER_PAGE_MIN_NOT_VALID)
                                                                                               @Max(value = 100, message = ServerValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100)
                                                                                               Integer perPage) {
        return newsService.getNewsByUserId(
                id,
                page,
                perPage).map(CustomSuccessResponse::new);
    }
}
