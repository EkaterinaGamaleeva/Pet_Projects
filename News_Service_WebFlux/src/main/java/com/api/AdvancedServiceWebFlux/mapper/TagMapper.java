package com.api.AdvancedServiceWebFlux.mapper;

import com.api.AdvancedServiceWebFlux.dto.response.TagResponse;
import com.api.AdvancedServiceWebFlux.models.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TagMapper {

    TagResponse tagToTagDto(Tag tag);
}
