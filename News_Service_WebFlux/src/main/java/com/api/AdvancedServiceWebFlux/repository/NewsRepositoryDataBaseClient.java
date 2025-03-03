package com.api.AdvancedServiceWebFlux.repository;

import com.api.AdvancedServiceWebFlux.mapper.NewsRowMapper;
import com.api.AdvancedServiceWebFlux.models.News;
import com.api.AdvancedServiceWebFlux.util.QueryConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class NewsRepositoryDataBaseClient {

    private final DatabaseClient databaseClient;

    private final NewsRowMapper newsRowMapper;

    public Mono<News> update(News news) {
        return databaseClient.sql(QueryConstants.UPDATE_NEWS)
                .bind("description", news.getDescription())
                .bind("image", news.getImage())
                .bind("title", news.getTitle())
                .bind("id", news.getNewsId())
                .map(newsRowMapper::mapRow)
                .one()
                .thenReturn(news)
                .flatMap(saveNews -> databaseClient.sql(QueryConstants.DELETE_TAGS_BY_NEWS_ID)
                        .bind("news_id", saveNews.getNewsId())
                        .map(newsRowMapper::mapRow)
                        .one()
                        .thenReturn(saveNews)
                        .flatMap(savedNews -> Flux.fromStream(savedNews.getTags().stream())
                                .flatMap(tag -> databaseClient.sql("INSERT INTO news_schema.news_tags (news_id, tag_id) VALUES (:news_id, :tag_id)")
                                        .bind("tag_id", tag.getTagId())
                                        .bind("news_id", savedNews.getNewsId())
                                        .fetch()
                                        .rowsUpdated())
                                .then()
                                .thenReturn(savedNews)));
    }

    public Mono<News> saveNews(News news) {
        return Mono.just(news)
                .flatMap(n -> databaseClient.sql(QueryConstants.INSERT_NEWS)
                        .bind("description", n.getDescription())
                        .bind("image", n.getImage())
                        .bind("title", n.getTitle())
                        .bind("users_id", n.getAuthor().getUserId())
                        .map((row, rowMetadata) -> {
                            news.setNewsId(row.get("news_id", Long.class));
                            return news;
                        })
                        .one()
                        .flatMap(savedNews -> Flux.fromStream(savedNews.getTags().stream())
                                .flatMap(tag -> databaseClient.sql("INSERT INTO news_schema.news_tags (news_id, tag_id) VALUES (:news_id, :tag_id)")
                                        .bind("tag_id", tag.getTagId())
                                        .bind("news_id", savedNews.getNewsId())
                                        .fetch()
                                        .rowsUpdated())
                                .then()
                                .thenReturn(savedNews)));
    }

    public Mono<List<News>> findAllNewsWithDetailsByUserId(UUID id, int offset, int size) {
        return databaseClient.sql(QueryConstants.FIND_ALL_NEWS_WITH_DETAILS_BY_USER_ID)
                .bind("id", id)
                .bind("offset", offset)
                .bind("size", size)
                .map(newsRowMapper::mapRow)
                .all()
                .collectList();
    }

    public Mono<News> findByIdNews(Long id) {
        return databaseClient.sql(QueryConstants.FIND_BY_ID_NEWS)
                .bind("id", id)
                .map(newsRowMapper::mapRow)
                .one();
    }

    public Mono<List<News>> findAllNewsDinamicWhere(String authorName,
                                                    String keywords,
                                                    Set<String> tags,
                                                    int offset, int size) {
        StringBuilder queryBuilder = new StringBuilder(QueryConstants.FIND_ALL_NEWS_DINAMIC_WHERE);
        Map<String, Object> bindings = new HashMap<>();

        if (authorName != null && !authorName.trim().isEmpty()) {
            queryBuilder.append(QueryConstants.AUTHOR_NAME_CONDITION);
            bindings.put("authorName", "%" + authorName + "%");
        }
        if (keywords != null && !keywords.trim().isEmpty()) {
            queryBuilder.append(QueryConstants.KEYWORDS_CONDITION);
            bindings.put("keywords", "%" + keywords + "%");
        }
        if (tags != null && !tags.isEmpty()) {
            queryBuilder.append(QueryConstants.TAGS_CONDITION);
            bindings.put("tags", tags.toArray(new String[0]));
        }
        queryBuilder.append(" GROUP BY n.news_id, u.users_id");
        queryBuilder.append(" OFFSET :offset LIMIT :size");

        var query = databaseClient.sql(queryBuilder.toString())
                .bind("offset", offset)
                .bind("size", size);

        for (Map.Entry<String, Object> entry : bindings.entrySet()) {
            query = query.bind(entry.getKey(), entry.getValue());
        }

        return query
                .map(newsRowMapper::mapRow)
                .all()
                .collectList();
    }

    public Mono<Long> deleteNewsById(Long newsId) {
        return databaseClient.sql(QueryConstants.DELETE_TAGS_BY_NEWS_ID)
                .bind("news_id", newsId)
                .fetch()
                .rowsUpdated()
                .then(databaseClient.sql(QueryConstants.DELETE_NEWS_BY_ID)
                        .bind("news_id", newsId)
                        .fetch()
                        .rowsUpdated()
                )
                .thenReturn(newsId);
    }

    public Mono<List<News>> findAllNews(int offset, Integer size) {
        return databaseClient.sql(QueryConstants.FIND_ALL)
                .bind("offset", offset)
                .bind("size", size)
                .map(newsRowMapper::mapRow)
                .all().collectList();
    }
}
