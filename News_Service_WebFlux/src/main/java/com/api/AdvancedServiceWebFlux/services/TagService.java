package com.api.AdvancedServiceWebFlux.services;

import com.api.AdvancedServiceWebFlux.models.Tag;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface TagService {

    Mono<Set<Tag>> parseTag(Set<String> tags);

    Mono<Void> deleteTagNotReferenceNewsTags();
}
