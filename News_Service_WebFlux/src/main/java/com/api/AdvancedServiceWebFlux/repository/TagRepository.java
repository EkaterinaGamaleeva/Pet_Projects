package com.api.AdvancedServiceWebFlux.repository;

import com.api.AdvancedServiceWebFlux.models.Tag;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface TagRepository extends ReactiveCrudRepository<Tag, Long> {

    Flux<Tag> findAllByTitleIn(Set<String> tags);

    @Query("delete from news_schema.tags " +
            "where news_schema.tags.tag_id not in (select distinct news_schema.news_tags.tag_id from news_schema.news_tags)")
    Mono<Void> deleteTagNotReferenceNewsTags();
}
