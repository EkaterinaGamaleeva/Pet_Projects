package com.dunice.projectNews.mapper;

import com.dunice.projectNews.dto.request.NewsRequest;
import com.dunice.projectNews.dto.response.GetNewsOutResponse;
import com.dunice.projectNews.dto.response.TagResponse;
import com.dunice.projectNews.models.News;
import com.dunice.projectNews.models.Tag;
import com.dunice.projectNews.models.User;
import com.dunice.projectNews.models.tables.records.NewsRecord;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-16T09:42:27+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class NewsMapperImpl implements NewsMapper {

    @Override
    public News newsRequestToNews(NewsRequest newsRequest) {
        if ( newsRequest == null ) {
            return null;
        }

        News news = new News();

        news.setDescription( newsRequest.getDescription() );
        news.setImage( newsRequest.getImage() );
        news.setTitle( newsRequest.getTitle() );

        return news;
    }

    @Override
    public GetNewsOutResponse newsToGetNewsDtoOut(News news) {
        if ( news == null ) {
            return null;
        }

        GetNewsOutResponse getNewsOutResponse = new GetNewsOutResponse();

        getNewsOutResponse.setId( news.getNewsId() );
        getNewsOutResponse.setUserId( uuidToString( newsAuthorUserId( news ) ) );
        getNewsOutResponse.setUsername( newsAuthorEmail( news ) );
        getNewsOutResponse.setDescription( news.getDescription() );
        getNewsOutResponse.setImage( news.getImage() );
        getNewsOutResponse.setTags( tagSetToTagResponseSet( news.getTags() ) );
        getNewsOutResponse.setTitle( news.getTitle() );

        return getNewsOutResponse;
    }

    @Override
    public News getNewsByNewsRecord(NewsRecord news) {
        if ( news == null ) {
            return null;
        }

        News news1 = new News();

        news1.setNewsId( news.getNewsId() );
        news1.setDescription( news.getDescription() );
        news1.setImage( news.getImage() );
        news1.setTitle( news.getTitle() );

        return news1;
    }

    @Override
    public List<GetNewsOutResponse> listNewsToListGetNewsDtoOut(List<News> news) {
        if ( news == null ) {
            return null;
        }

        List<GetNewsOutResponse> list = new ArrayList<GetNewsOutResponse>( news.size() );
        for ( News news1 : news ) {
            list.add( newsToGetNewsDtoOut( news1 ) );
        }

        return list;
    }

    @Override
    public News newsRequestupdateNews(NewsRequest newsRequest, News news) {
        if ( newsRequest == null ) {
            return news;
        }

        news.setDescription( newsRequest.getDescription() );
        news.setImage( newsRequest.getImage() );
        news.setTitle( newsRequest.getTitle() );

        return news;
    }

    private UUID newsAuthorUserId(News news) {
        if ( news == null ) {
            return null;
        }
        User author = news.getAuthor();
        if ( author == null ) {
            return null;
        }
        UUID userId = author.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }

    private String newsAuthorEmail(News news) {
        if ( news == null ) {
            return null;
        }
        User author = news.getAuthor();
        if ( author == null ) {
            return null;
        }
        String email = author.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    protected TagResponse tagToTagResponse(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        TagResponse tagResponse = new TagResponse();

        tagResponse.setTagId( tag.getTagId() );
        tagResponse.setTitle( tag.getTitle() );

        return tagResponse;
    }

    protected Set<TagResponse> tagSetToTagResponseSet(Set<Tag> set) {
        if ( set == null ) {
            return null;
        }

        Set<TagResponse> set1 = new LinkedHashSet<TagResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Tag tag : set ) {
            set1.add( tagToTagResponse( tag ) );
        }

        return set1;
    }
}
