package com.api.AdvancedServiceWebFlux.services;

import com.api.AdvancedServiceWebFlux.dto.request.NewsRequest;
import com.api.AdvancedServiceWebFlux.dto.response.GetNewsOutResponse;
import com.api.AdvancedServiceWebFlux.dto.response.PageableResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface NewsService {
    Mono<PageableResponse<List<GetNewsOutResponse>>> getNews(Integer page, Integer perPage);

    Mono<Long> createNews(NewsRequest newsRequest);

    Mono<Long> putNewsById(Long id, NewsRequest newsRequest);

    Mono<Long> deleteNewsById(Long id);

    Mono<PageableResponse<List<GetNewsOutResponse>>> getNewsFind(Integer page,
                                                                 Integer perPage,
                                                                 String author,
                                                                 String keywords,
                                                                 Set<String> tags);

    Mono<PageableResponse<List<GetNewsOutResponse>>> getNewsByUserId(UUID id, Integer page, Integer perPage);
}
