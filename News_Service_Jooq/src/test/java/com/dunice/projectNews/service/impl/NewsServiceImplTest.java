package com.dunice.projectNews.service.impl;

import com.dunice.projectNews.constants.TestConstants;
import com.dunice.projectNews.dto.response.GetNewsOutResponse;
import com.dunice.projectNews.dto.request.NewsRequest;
import com.dunice.projectNews.dto.response.PageableResponse;
import com.dunice.projectNews.dto.common.BaseSuccessResponse;
import com.dunice.projectNews.exception.CustomException;
import com.dunice.projectNews.mapper.NewsMapper;
import com.dunice.projectNews.models.News;
import com.dunice.projectNews.models.Tag;
import com.dunice.projectNews.models.User;
import com.dunice.projectNews.repository.NewsRepository;
import com.dunice.projectNews.services.TagService;
import com.dunice.projectNews.services.UserService;
import com.dunice.projectNews.services.impl.NewsServiceImpl;
import com.dunice.projectNews.util.ServerErrorCodes;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        newsRequest.setTitle(TestConstants.TITLE);
        news = new News();
        news.setTags(Set.of(new Tag()));
        news.setTitle(TestConstants.TITLE);
        news.setAuthor(new User());
        news.setNewsId(TestConstants.NEWS_ID);
    }

    @Test
    public void createNews_CreateNewsSuccessResponse_createNewsSuccess() {
        SecurityContextHolder.setContext(securityContext);
        when(newsMapper.newsRequestToNews(newsRequest)).thenReturn(news);
        doNothing().when(userService).saveUserForNews(news);
        when(tagService.parseTag(newsRequest)).thenReturn(Set.of(new Tag().getTagId()));
        BaseSuccessResponse request = new BaseSuccessResponse();
        request.setId(news.getNewsId());
        BaseSuccessResponse response =
                new BaseSuccessResponse();
        response.setId(newsService.createNews(newsRequest));
        assertNotNull(response);
        assertEquals(request, response);
    }

    @Test
    public void getNews_PageableResponseListGetNewsOutDto_getNewsSuccess() {
        when(newsMapper.listNewsToListGetNewsDtoOut(any())).thenReturn(List.of(new GetNewsOutResponse()));
        PageableResponse<List<GetNewsOutResponse>> response =
                newsService.getNews(TestConstants.ONE, TestConstants.ONE_HUNDRED);
        assertNotNull(response);
        assertEquals(TestConstants.ONE, response.getNumberOfElements());
        assertEquals(new GetNewsOutResponse(),response.getContent().get(0));
    }

    @Test
    public void put_BaseSuccessResponse_putSuccess() {
        SecurityContextHolder.setContext(securityContext);
        when(newsRepository.getNewsById(TestConstants.NEWS_ID)).thenReturn(news);
        when(userService.checkingUserCurrentAuthenticationUser(news.getAuthor())).thenReturn(true);
        when(newsMapper.newsRequestupdateNews(newsRequest, news)).thenReturn(news);
        when(tagService.parseTag(newsRequest)).thenReturn(Set.of(new Tag().getTagId()));
        newsService.putNewsById(TestConstants.NEWS_ID, newsRequest);
        verify(newsRepository, times(TestConstants.ONE)).saveNews(news,Set.of(new Tag().getTagId()));
    }

    @Test
    public void put_CustomExceptionNoRightToChangeNews_putNoSuccess() {
        SecurityContextHolder.setContext(securityContext);
        when(newsRepository.getNewsById(TestConstants.NEWS_ID)).thenReturn(news);
        when(userService.checkingUserCurrentAuthenticationUser(news.getAuthor())).thenReturn(false);
        CustomException exception = assertThrows(CustomException.class,
                () -> newsService.putNewsById(TestConstants.NEWS_ID, newsRequest));
        assertEquals(ServerErrorCodes
                        .NO_RIGHT_TO_CHANGE_NEWS
                        .getMessage(),
                exception.getMessage());
    }

    @Test
    public void put_CustomExceptionNewsNotFound_putNoSuccess() {
        SecurityContextHolder.setContext(securityContext);
        when(newsRepository.getNewsById(TestConstants.NEWS_ID)).thenReturn(null);
        CustomException exception = assertThrows(CustomException.class,
                () -> newsService.putNewsById(TestConstants.NEWS_ID, newsRequest));
        assertEquals(ServerErrorCodes
                        .NEWS_NOT_FOUND
                        .getMessage(),
                exception.getMessage());
    }

    @Test
    public void delete_CustomExceptionNewsNotFound_deleteNoSuccess() {
        SecurityContextHolder.setContext(securityContext);
        when(newsRepository.getNewsById(TestConstants.NEWS_ID)).thenReturn(null);
        CustomException exception = assertThrows(CustomException.class,
                () -> newsService.deleteNewsById(TestConstants.NEWS_ID));
        assertEquals(ServerErrorCodes
                        .NEWS_NOT_FOUND
                        .getMessage(),
                exception.getMessage());
    }

    @Test
    public void delete_CustomExceptionNoRightToChangeNews_deleteSuccess() {
        SecurityContextHolder.setContext(securityContext);
        when(newsRepository.getNewsById(news.getNewsId())).thenReturn(news);
        when(userService.checkingUserCurrentAuthenticationUser(news.getAuthor())).thenReturn(false);
        CustomException exception = assertThrows(CustomException.class,
                () -> newsService.deleteNewsById(news.getNewsId()));
        assertEquals(ServerErrorCodes
                        .NO_RIGHT_TO_CHANGE_NEWS
                        .getMessage(),
                exception.getMessage());
    }

    @Test
    public void delete_BaseSuccessResponse_deleteSuccess() {
        SecurityContextHolder.setContext(securityContext);
        when(newsRepository.getNewsById(TestConstants.NEWS_ID)).thenReturn(news);
        when(userService.checkingUserCurrentAuthenticationUser(news.getAuthor())).thenReturn(true);
        newsService.deleteNewsById(news.getNewsId());
        verify(newsRepository, times(TestConstants.ONE)).deleteById(news.getNewsId());
    }

    @Test
    public void getUserNews_PageableResponseListGetNewsOutDto_getUserNewsSuccess() {
        SecurityContextHolder.setContext(securityContext);
        when(newsRepository.getNewsByUserID(TestConstants.USER_ID_UUID,
               TestConstants.ZERO, TestConstants.ONE_HUNDRED))
                .thenReturn(List.of(new GetNewsOutResponse()));
        when(newsMapper.listNewsToListGetNewsDtoOut(any())).thenReturn(List.of(new GetNewsOutResponse()));
        PageableResponse<List<GetNewsOutResponse>> response = newsService.getUserNews(TestConstants.USER_ID_UUID,
                TestConstants.ONE,
                TestConstants.ONE_HUNDRED);
        assertNotNull(response);
        assertEquals(TestConstants.ONE,
                response.getNumberOfElements());
    }

    @Test
    public void getNewsFind_PageableResponseListGetNewsOutDto_getNewsFindSuccess() {
        SecurityContextHolder.setContext(securityContext);
        GetNewsOutResponse expectedGetNewsOutResponse = new GetNewsOutResponse();
        expectedGetNewsOutResponse.setTitle(TestConstants.TITLE);
        when(newsRepository.findAllNewsDinamicWhere(TestConstants.TITLE,
                TestConstants.TITLE,
                Set.of(TestConstants.TITLE),
               TestConstants.ZERO,
                        TestConstants.ONE_HUNDRED))
                .thenReturn(List.of(expectedGetNewsOutResponse));
        when(newsMapper.listNewsToListGetNewsDtoOut(any())).thenReturn(List.of(expectedGetNewsOutResponse));
        PageableResponse<List<GetNewsOutResponse>> expectedResponse =
                new PageableResponse<>(List.of(expectedGetNewsOutResponse));
        expectedResponse.setNumberOfElements(TestConstants.ONE);
        PageableResponse<List<GetNewsOutResponse>> response = newsService.getNewsFind(TestConstants.ONE,
                TestConstants.ONE_HUNDRED,
                TestConstants.TITLE,
                TestConstants.TITLE,
                Set.of(TestConstants.TITLE));
        assertEquals(expectedResponse,response);
    }
}
