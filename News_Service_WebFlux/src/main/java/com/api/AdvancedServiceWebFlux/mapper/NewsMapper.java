package com.api.AdvancedServiceWebFlux.mapper;

import com.api.AdvancedServiceWebFlux.dto.request.NewsRequest;
import com.api.AdvancedServiceWebFlux.dto.response.GetNewsOutResponse;
import com.api.AdvancedServiceWebFlux.models.News;
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


    List<GetNewsOutResponse> listNewsToListGetNewsOutResponse(List<News> news);


    @Mapping(target = "id", source = "newsId")
    @Mapping(target = "userId", source = "author.userId", qualifiedByName = "uuidToString")
    @Mapping(target = "username", source = "author.email")
    GetNewsOutResponse newsToGetNewsDtoOut(News news);


    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "newsId", ignore = true)
    @Mapping(target = "author", ignore = true)
    News newsRequestupdateNews(NewsRequest newsRequest, @MappingTarget News news);

    @Named("uuidToString")
    default String uuidToString(UUID uuid) {
        return uuid != null ? uuid.toString() : null;

    }
}


