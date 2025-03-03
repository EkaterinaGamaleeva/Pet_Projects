package com.dunice.projectNews.repository;

import com.dunice.projectNews.dto.response.GetNewsOutResponse;
import com.dunice.projectNews.models.News;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface NewsRepository {

    List<GetNewsOutResponse> findAllNewsDinamicWhere(String authorName,
                                 String keywords,
                                 Set<String> tags,
                                 Integer page, Integer perPage);

    List<GetNewsOutResponse> getNewsByUserID(UUID id, int page, int perPage);

    void saveNews(News news, Set<Long> tagId);

    List<GetNewsOutResponse> getPaginatedNews(int page, int perPage);

    void putNews(News news, Set<Long> tagId);

    News getNewsById(Long id);

    void deleteById(Long id);
}
