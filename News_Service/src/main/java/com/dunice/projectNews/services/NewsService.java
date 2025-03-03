package com.dunice.projectNews.services;

import com.dunice.projectNews.dto.response.GetNewsOutResponse;
import com.dunice.projectNews.dto.request.NewsRequest;
import com.dunice.projectNews.dto.response.PageableResponse;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface NewsService {
    PageableResponse<List<GetNewsOutResponse>> getNews(Integer page, Integer perPage);

    Long createNews(NewsRequest newsRequest);

    void putNewsById(Long id, NewsRequest newsRequest);

    void deleteNewsById(Long id);

    PageableResponse<List<GetNewsOutResponse>> getNewsFind(Integer page,
                                                           Integer perPage,
                                                           String author,
                                                           String keywords,
                                                           Set<String> tags);

    PageableResponse<List<GetNewsOutResponse>> getUserNews(UUID id, Integer page, Integer perPage);
}
