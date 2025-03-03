package com.dunice.projectNews.mapper;

import com.dunice.projectNews.dto.response.TagResponse;
import com.dunice.projectNews.models.Tag;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-16T09:42:27+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class TagMapperImpl implements TagMapper {

    @Override
    public TagResponse tagToTagDto(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        TagResponse tagResponse = new TagResponse();

        tagResponse.setTagId( tag.getTagId() );
        tagResponse.setTitle( tag.getTitle() );

        return tagResponse;
    }
}
