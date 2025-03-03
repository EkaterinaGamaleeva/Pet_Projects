package com.dunice.projectNews.mapper;

import com.dunice.projectNews.dto.response.TagResponse;
import com.dunice.projectNews.models.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TagMapper {
    TagResponse tagToTagDto(Tag tag);
}
