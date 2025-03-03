package com.dunice.advancedServiceKotlin.mapper

import com.dunice.advancedServiceKotlin.dto.response.TagResponse
import com.dunice.advancedServiceKotlin.models.Tag
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface TagMapper {
    fun tagToTagDto(tag: Tag): TagResponse
}
