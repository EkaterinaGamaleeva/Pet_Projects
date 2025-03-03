package com.dunice.projectNews.mapper;

import com.dunice.projectNews.dto.response.GetNewsOutResponse;
import com.dunice.projectNews.dto.request.NewsRequest;
import com.dunice.projectNews.models.News;
import com.dunice.projectNews.models.tables.records.NewsRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewsMapper {

    @Mapping(target = "tags", ignore = true)
    News newsRequestToNews(NewsRequest newsRequest);

    @Mapping(target = "id", source = "newsId")
    @Mapping(target = "userId", source = "author.userId", qualifiedByName = "uuidToString")
    @Mapping(target = "username", source = "author.email")
    GetNewsOutResponse newsToGetNewsDtoOut(News news);

    News getNewsByNewsRecord(NewsRecord news);

    List<GetNewsOutResponse> listNewsToListGetNewsDtoOut(List<News> news);

    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "newsId", ignore = true)
    @Mapping(target = "author", ignore = true)
    News newsRequestupdateNews(NewsRequest newsRequest, @MappingTarget News news);

    @Named("uuidToString")
    default String uuidToString(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }
}
