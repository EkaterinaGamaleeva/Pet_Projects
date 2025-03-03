package com.api.AdvancedServiceWebFlux.services.impl;

import com.api.AdvancedServiceWebFlux.models.Tag;
import com.api.AdvancedServiceWebFlux.repository.TagRepository;
import com.api.AdvancedServiceWebFlux.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Mono<Set<Tag>> parseTag(Set<String> tags) {
        return tagRepository.findAllByTitleIn(tags).collectList()
                .flatMap(existingTags -> {
                    Set<Tag> existingTagsSet = new HashSet<>(existingTags);
                    Set<Tag> tagsToSave = new HashSet<>();

                    for (String title : tags) {
                        Tag tagsEntity = existingTagsSet.stream()
                                .filter(t -> t.getTitle().equals(title))
                                .findFirst()
                                .orElseGet(() -> {
                                    Tag newTag = new Tag();
                                    newTag.setTitle(title);
                                    return newTag;
                                });
                        tagsToSave.add(tagsEntity);
                    }
                    return tagRepository.saveAll(tagsToSave).collectList().map(HashSet::new);
                });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Mono<Void> deleteTagNotReferenceNewsTags() {
        return tagRepository.deleteTagNotReferenceNewsTags();
    }
}
