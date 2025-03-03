package com.api.AdvancedServiceWebFlux.services.impl;

import com.api.AdvancedServiceWebFlux.dto.request.NewsRequest;
import com.api.AdvancedServiceWebFlux.dto.response.GetNewsOutResponse;
import com.api.AdvancedServiceWebFlux.dto.response.PageableResponse;
import com.api.AdvancedServiceWebFlux.exception.CustomException;
import com.api.AdvancedServiceWebFlux.mapper.NewsMapper;
import com.api.AdvancedServiceWebFlux.models.News;
import com.api.AdvancedServiceWebFlux.repository.NewsRepositoryDataBaseClient;
import com.api.AdvancedServiceWebFlux.services.NewsService;
import com.api.AdvancedServiceWebFlux.services.TagService;
import com.api.AdvancedServiceWebFlux.services.UserService;
import com.api.AdvancedServiceWebFlux.util.ServerErrorCodes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsMapper newsMapper;

    private final UserService userService;

    private final TagService tagService;

    private final NewsRepositoryDataBaseClient newsRepositoryDatabaseclient;

    private final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Override
    public Mono<PageableResponse<List<GetNewsOutResponse>>> getNews(Integer page, Integer perPage) {
        return newsRepositoryDatabaseclient.findAllNews(page - 1, perPage)
                .map(newsMapper::listNewsToListGetNewsOutResponse)
                .map(e -> new PageableResponse<>(e, e.size()));
    }

    @Override
    @Transactional
    public Mono<Long> createNews(NewsRequest newsRequest) {
        return Mono.just(newsRequest)
                .map(newsMapper::newsRequestToNews)
                .flatMap(newsEntity ->
                        userService.getUserAuthentication()
                                .doOnNext(newsEntity::setAuthor)
                                .thenReturn(newsEntity)
                )
                .flatMap(newsEntity ->
                        tagService.parseTag(newsRequest.getTags())
                                .doOnNext(newsEntity::setTags)
                                .thenReturn(newsEntity)
                )
                .flatMap(newsRepositoryDatabaseclient::saveNews)
                .map(News::getNewsId);
    }

    @Override
    @Transactional
    public Mono<Long> putNewsById(Long id, NewsRequest newsRequest) {
        return newsRepositoryDatabaseclient.findByIdNews(id)
                .switchIfEmpty(Mono.error(new CustomException(ServerErrorCodes.NEWS_NOT_FOUND)))
                .doOnSuccess(
                        e ->
                                userService
                                        .checkingUserCurrentAuthenticationUser(e.getAuthor().getUserId()))
                .map(n -> {
                    News news = newsMapper.newsRequestupdateNews(newsRequest, n);
                    news.setNewsId(n.getNewsId());
                    news.setAuthor(n.getAuthor());
                    return news;
                })
                .flatMap(news ->
                        tagService.parseTag(newsRequest.getTags())
                                .doOnNext(news::setTags)
                                .thenReturn(news))
                .flatMap(newsRepositoryDatabaseclient::update)
                .doOnSuccess(e->tagService
                        .deleteTagNotReferenceNewsTags()
                        .subscribe())
                .map(News::getNewsId)
                .switchIfEmpty(Mono.error(new CustomException(ServerErrorCodes.NEWS_NOT_FOUND)));
    }

    @Override
    @Transactional
    public Mono<Long> deleteNewsById(Long id) {
        return newsRepositoryDatabaseclient.findByIdNews(id)
                .switchIfEmpty(Mono.error(new CustomException(ServerErrorCodes.NEWS_NOT_FOUND)))
                .doOnSuccess(e ->
                        userService.checkingUserCurrentAuthenticationUser(e.getAuthor().getUserId()))
                .flatMap(n->newsRepositoryDatabaseclient.deleteNewsById(n.getNewsId()))
                .doOnSuccess(e->tagService
                        .deleteTagNotReferenceNewsTags()
                        .subscribe())
                .switchIfEmpty(Mono.error(new CustomException(ServerErrorCodes.NO_RIGHT_TO_CHANGE_NEWS)));

    }

    @Override
    public Mono<PageableResponse<List<GetNewsOutResponse>>> getNewsFind(Integer page, Integer perPage, String
            author, String keywords, Set<String> tags) {
        return newsRepositoryDatabaseclient.findAllNewsDinamicWhere(author, keywords, tags, page - 1, perPage)
                .switchIfEmpty(Mono.error(new CustomException(ServerErrorCodes.NEWS_NOT_FOUND)))
                .map(newsMapper::listNewsToListGetNewsOutResponse)
                .map(e -> {
                            PageableResponse<List<GetNewsOutResponse>> pageableResponse =
                                    new PageableResponse<>(e);
                            pageableResponse.setNumberOfElements(e.size());
                            return pageableResponse;
                        }
                );
    }

    @Override
    public Mono<PageableResponse<List<GetNewsOutResponse>>> getNewsByUserId(UUID id, Integer page, Integer perPage) {
        return newsRepositoryDatabaseclient.findAllNewsWithDetailsByUserId(id, page - 1, perPage)
                .switchIfEmpty(Mono.error(new CustomException(ServerErrorCodes.NEWS_NOT_FOUND)))
                .map(newsMapper::listNewsToListGetNewsOutResponse)
                .map(e -> {
                            PageableResponse<List<GetNewsOutResponse>> pageableResponse =
                                    new PageableResponse<>(e);
                            pageableResponse.setNumberOfElements(e.size());
                            return pageableResponse;
                        }
                );
    }
}
