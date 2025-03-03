package com.dunice.projectNews.services.impl;

import com.dunice.projectNews.dto.response.GetNewsOutResponse;
import com.dunice.projectNews.dto.request.NewsRequest;
import com.dunice.projectNews.dto.response.PageableResponse;
import com.dunice.projectNews.exception.CustomException;
import com.dunice.projectNews.mapper.NewsMapper;
import com.dunice.projectNews.models.News;
import com.dunice.projectNews.repository.NewsRepository;
import com.dunice.projectNews.services.NewsService;
import com.dunice.projectNews.services.TagService;
import com.dunice.projectNews.services.UserService;
import com.dunice.projectNews.util.ServerErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    private final UserService userService;

    private final TagService tagService;

    @Override
    public PageableResponse<List<GetNewsOutResponse>> getNews(Integer page, Integer perPage) {
        Pageable pageable = PageRequest.of(page - 1, perPage);

        List<GetNewsOutResponse> getNews = newsMapper
                .listNewsToListGetNewsDtoOut(newsRepository
                        .findAll(pageable)
                        .getContent());

        return new PageableResponse<>(getNews, getNews.size());
    }

    @Override
    @Transactional
    public Long createNews(NewsRequest newsRequest) {
        News news = newsMapper.newsRequestToNews(newsRequest);
        userService.saveUserForNews(news);
        news.setTags(tagService.parseTag(newsRequest));
        newsRepository.save(news);
        return news.getNewsId();
    }

    @Override
    @Transactional
    public void putNewsById(Long id, NewsRequest newsRequest) {
        News getNewsEntity = newsRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ServerErrorCodes.NEWS_NOT_FOUND));
        if (userService.checkingUserCurrentAuthenticationUser(getNewsEntity.getAuthor())) {
            News news = newsMapper.newsRequestupdateNews(newsRequest, getNewsEntity);
            news.setNewsId(getNewsEntity.getNewsId());
            news.setAuthor(getNewsEntity.getAuthor());
            news.setTags(tagService.parseTag(newsRequest));
            newsRepository.save(news);
        } else {
            throw new CustomException(ServerErrorCodes.NO_RIGHT_TO_CHANGE_NEWS);
        }
    }

    @Override
    public void deleteNewsById(Long id) {
        if (userService
                .checkingUserCurrentAuthenticationUser(newsRepository
                        .findById(id)
                        .orElseThrow(() -> new CustomException(ServerErrorCodes.NEWS_NOT_FOUND))
                        .getAuthor())) {
            newsRepository.deleteById(id);
        } else {
            throw new CustomException(ServerErrorCodes.NO_RIGHT_TO_CHANGE_NEWS);
        }
    }

    @Override
    public PageableResponse<List<GetNewsOutResponse>> getNewsFind(Integer page,
                                                                  Integer perPage,
                                                                  String author,
                                                                  String keywords,
                                                                  Set<String> tags) {
        Pageable pageable = PageRequest.of(page - 1, perPage);

        List<GetNewsOutResponse> getNews = newsMapper
                .listNewsToListGetNewsDtoOut(newsRepository
                .findAllNewsDinamicWhere(
                        author,
                        keywords,
                        tags, pageable)
                .getContent());

        PageableResponse<List<GetNewsOutResponse>> pageableResponse = new PageableResponse<>(getNews);

        pageableResponse.setNumberOfElements(getNews.size());
        return pageableResponse;
    }

    @Override
    public PageableResponse<List<GetNewsOutResponse>> getUserNews(UUID id, Integer page, Integer perPage) {

        Pageable pageable = PageRequest.of(page - 1, perPage);

        List<GetNewsOutResponse> getNewsOutResponseList = newsMapper
                .listNewsToListGetNewsDtoOut(newsRepository
                        .findAllNewsWithDetailsByUserId(id, pageable)
                        .getContent());

        PageableResponse<List<GetNewsOutResponse>> pageableResponse =
                new PageableResponse<>(getNewsOutResponseList);

        pageableResponse.setNumberOfElements(getNewsOutResponseList.size());

        return pageableResponse;
    }
}
