package com.dunice.advancedServiceKotlin.impl;

import com.dunice.advancedServiceKotlin.constants.TestConstants;
import com.dunice.advancedServiceKotlin.dto.common.BaseSuccessResponse;
import com.dunice.advancedServiceKotlin.dto.request.NewsRequest;
import com.dunice.advancedServiceKotlin.dto.response.GetNewsOutResponse;
import com.dunice.advancedServiceKotlin.dto.response.PageableResponse;
import com.dunice.advancedServiceKotlin.exception.CustomException;
import com.dunice.advancedServiceKotlin.mapper.NewsMapper;
import com.dunice.advancedServiceKotlin.models.News;
import com.dunice.advancedServiceKotlin.models.Tag;
import com.dunice.advancedServiceKotlin.models.User;
import com.dunice.advancedServiceKotlin.repository.NewsRepository;
import com.dunice.advancedServiceKotlin.services.TagService;
import com.dunice.advancedServiceKotlin.services.UserService;
import com.dunice.advancedServiceKotlin.services.impl.NewsServiceImpl;
import com.dunice.advancedServiceKotlin.util.ServerErrorCodes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsServiceImplTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private NewsMapper newsMapper;

    @Mock
    private UserService userService;

    @Mock
    private TagService tagService;

    @InjectMocks
    private NewsServiceImpl newsService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private NewsRequest newsRequest;

    private News news;


    @BeforeEach
    void setUp() {
        newsRequest = new NewsRequest();
        newsRequest.setTags(Set.of(TestConstants.TITLE));
        newsRequest.title = TestConstants.TITLE;
        news = new News();
        news.setTags(Set.of(new Tag()));
        news.title = TestConstants.TITLE;
        news.author = new User();
        news.newsId = TestConstants.NEWS_ID;
    }

    @Test
    public void createNews_CreateNewsSuccessResponse_createNewsSuccess() {
        SecurityContextHolder.setContext(securityContext);
        when(newsMapper.newsRequestToNews(newsRequest)).thenReturn(news);
        doNothing().when(userService).saveUserForNews(news);
        when(tagService.parseTag(newsRequest)).thenReturn(Set.of(new Tag()));
        when(newsRepository.save(news)).thenReturn(news);
        BaseSuccessResponse request = new BaseSuccessResponse();
        request.id = news.newsId;
        BaseSuccessResponse response =
                new BaseSuccessResponse();
        response.id = newsService.createNews(newsRequest);
        assertNotNull(response);
        assertEquals(request, response);
    }

    @Test
    public void getNews_PageableResponseListGetNewsOutDto_getNewsSuccess() {
        when(newsRepository
                .findAll(PageRequest.of(TestConstants.ZERO, TestConstants.ONE_HUNDRED)))
                .thenReturn(new PageImpl<>(List.of(new News())));
        when(newsMapper.listNewsToListGetNewsDtoOut(any())).thenReturn(List.of(new GetNewsOutResponse()));
        PageableResponse<List<GetNewsOutResponse>> response =
                newsService.getNews(TestConstants.ONE, TestConstants.ONE_HUNDRED);
        assertNotNull(response);
        assertEquals(TestConstants.ONE, response.numberOfElements);
        assertEquals(new GetNewsOutResponse(),response.getContent().get(0));
    }

    @Test
    public void put_BaseSuccessResponse_putSuccess() {
        SecurityContextHolder.setContext(securityContext);
        when(newsRepository.findById(TestConstants.NEWS_ID)).thenReturn(Optional.of(news));
        when(userService.checkingUserCurrentAuthenticationUser(news.author)).thenReturn(true);
        when(newsMapper.newsRequestupdateNews(newsRequest, news)).thenReturn(news);
        when(tagService.parseTag(newsRequest)).thenReturn(Set.of(new Tag()));
        newsService.putNewsById(TestConstants.NEWS_ID, newsRequest);
        verify(newsRepository, times(TestConstants.ONE)).save(news);
    }

    @Test
    public void put_CustomExceptionNoRightToChangeNews_putNoSuccess() {
        SecurityContextHolder.setContext(securityContext);
        when(newsRepository.findById(TestConstants.NEWS_ID)).thenReturn(Optional.of(news));
        when(userService.checkingUserCurrentAuthenticationUser(news.author)).thenReturn(false);
        CustomException exception = assertThrows(CustomException.class,
                () -> newsService.putNewsById(TestConstants.NEWS_ID, newsRequest));
        assertEquals(ServerErrorCodes
                        .NO_RIGHT_TO_CHANGE_NEWS.message,
                exception.getMessage());
    }

    @Test
    public void put_CustomExceptionNewsNotFound_putNoSuccess() {
        SecurityContextHolder.setContext(securityContext);
        when(newsRepository.findById(TestConstants.NEWS_ID)).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class,
                () -> newsService.putNewsById(TestConstants.NEWS_ID, newsRequest));
        assertEquals(ServerErrorCodes
                        .NEWS_NOT_FOUND.message,
                exception.getMessage());
    }

    @Test
    public void delete_CustomExceptionNewsNotFound_deleteNoSuccess() {
        SecurityContextHolder.setContext(securityContext);
        when(newsRepository.findById(TestConstants.NEWS_ID)).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class,
                () -> newsService.deleteNewsById(TestConstants.NEWS_ID));
        assertEquals(ServerErrorCodes
                        .NEWS_NOT_FOUND.message,
                exception.getMessage());
    }

    @Test
    public void delete_CustomExceptionNoRightToChangeNews_deleteSuccess() {
        SecurityContextHolder.setContext(securityContext);
        when(newsRepository.findById(news.newsId)).thenReturn(Optional.of(news));
        when(userService.checkingUserCurrentAuthenticationUser(news.author)).thenReturn(false);
        CustomException exception = assertThrows(CustomException.class,
                () -> newsService.deleteNewsById(news.newsId));
        assertEquals(ServerErrorCodes
                        .NO_RIGHT_TO_CHANGE_NEWS.message,
                exception.getMessage());
    }

    @Test
    public void delete_BaseSuccessResponse_deleteSuccess() {
        SecurityContextHolder.setContext(securityContext);
        when(newsRepository.findById(TestConstants.NEWS_ID)).thenReturn(Optional.of(news));
        when(userService.checkingUserCurrentAuthenticationUser(news.author)).thenReturn(true);
        newsService.deleteNewsById(news.newsId);
        verify(newsRepository, times(TestConstants.ONE)).deleteById(news.newsId);
    }

    @Test
    public void getUserNews_PageableResponseListGetNewsOutDto_getUserNewsSuccess() {
        SecurityContextHolder.setContext(securityContext);
        when(newsRepository.findAllNewsWithDetailsByUserId(TestConstants.USER_ID_UUID,
                PageRequest.of(TestConstants.ZERO, TestConstants.ONE_HUNDRED)))
                .thenReturn(new PageImpl<>(List.of(new News())));
        when(newsMapper.listNewsToListGetNewsDtoOut(any())).thenReturn(List.of(new GetNewsOutResponse()));
        PageableResponse<List<GetNewsOutResponse>> response = newsService.getUserNews(TestConstants.USER_ID_UUID,
                TestConstants.ONE,
                TestConstants.ONE_HUNDRED);
        assertNotNull(response);
        assertEquals(TestConstants.ONE,
                response.numberOfElements);
    }

    @Test
    public void getNewsFind_PageableResponseListGetNewsOutDto_getNewsFindSuccess() {
        SecurityContextHolder.setContext(securityContext);
        GetNewsOutResponse expectedGetNewsOutResponse = new GetNewsOutResponse();
        expectedGetNewsOutResponse.setTitle(TestConstants.TITLE);
        when(newsRepository.findAllNewsDinamicWhere(TestConstants.TITLE,
                TestConstants.TITLE,
                Set.of(TestConstants.TITLE),
                PageRequest.of(TestConstants.ZERO,
                        TestConstants.ONE_HUNDRED)))
                .thenReturn(new PageImpl(List.of(expectedGetNewsOutResponse)));
        when(newsMapper.listNewsToListGetNewsDtoOut(any())).thenReturn(List.of(expectedGetNewsOutResponse));
        PageableResponse<List<GetNewsOutResponse>> expectedResponse =
                new PageableResponse<>(List.of(expectedGetNewsOutResponse));
        expectedResponse.numberOfElements = TestConstants.ONE;
        PageableResponse<List<GetNewsOutResponse>> response = newsService.getNewsFind(TestConstants.ONE,
                TestConstants.ONE_HUNDRED,
                TestConstants.TITLE,
                TestConstants.TITLE,
                Set.of(TestConstants.TITLE));
        assertEquals(expectedResponse,response);
    }
}
