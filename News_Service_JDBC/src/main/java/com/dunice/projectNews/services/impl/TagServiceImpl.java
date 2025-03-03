package com.dunice.projectNews.services.impl;

import com.dunice.projectNews.dto.request.NewsRequest;
import com.dunice.projectNews.models.Tag;
import com.dunice.projectNews.repository.TagRepositoryJDBC;
import com.dunice.projectNews.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepositoryJDBC tagRepositoryJDBC;

    @Transactional
    public Set<Tag> parseTag(NewsRequest newsRequest) {
        Set<Tag> tagsForNonExistent = new HashSet<>();


        Set<Tag> tagsSet = newsRequest.getTags().stream().map(s -> {
            return tagRepositoryJDBC.findByTitle(s).orElseGet(() -> {
                Tag newTag = new Tag();
                newTag.setTitle(s);
                tagsForNonExistent.add(newTag);
                return newTag;
            });
        }).collect(Collectors.toSet());

        Set<Tag> saveTag = new HashSet<>();
        if (!tagsForNonExistent.isEmpty()) {
            saveTag = tagRepositoryJDBC.saveAll(tagsForNonExistent);
        }

        Set<Tag> finalSaveTag = saveTag;

        tagsSet = tagsSet.stream()
                .map(tag -> {
                    if (tag.getTagId() == null) {
                        return finalSaveTag.stream()
                                .filter(savedTag -> savedTag.getTitle().equals(tag.getTitle()))
                                .findFirst()
                                .orElse(tag);
                    }
                    return tag;
                })
                .collect(Collectors.toSet());

        return tagsSet;
    }


}