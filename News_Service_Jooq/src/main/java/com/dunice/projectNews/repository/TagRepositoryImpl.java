package com.dunice.projectNews.repository;

import com.dunice.projectNews.dto.request.NewsRequest;
import com.dunice.projectNews.models.Tag;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static com.dunice.projectNews.models.tables.Tags.TAGS;

@RequiredArgsConstructor
@Repository
public class TagRepositoryImpl implements TagRepository {

    private final DSLContext dsl;

    public Set<Long> saveNewsWithTags(NewsRequest newsRequest) {
        Set<Long> insertedIds = new HashSet<>();
        Set<Tag> tagsForNonExistent = new HashSet<>();

        Set<Long> tagsIdExistent= newsRequest.getTags().stream().map(tagTitle -> {
            return dsl.selectFrom(TAGS)
                    .where(TAGS.TITLE.eq(tagTitle))
                    .fetchOptional(TAGS.TAG_ID)
                    .orElseGet(() -> {
                        Tag newTag = new Tag();
                        newTag.setTitle(tagTitle);
                        tagsForNonExistent.add(newTag);
                        return null;
                    });
        }).collect(Collectors.toSet());
        tagsIdExistent.removeIf(Objects::isNull);
        if (!tagsForNonExistent.isEmpty()) {
          insertedIds = tagsForNonExistent.stream()
                    .map(tag -> {
                        var record = dsl.newRecord(TAGS, tag);
                        return dsl.insertInto(TAGS)
                                .set(record)
                                .returning(TAGS.TAG_ID)
                                .fetchOne()
                                .getValue(TAGS.TAG_ID);
                    })
                    .collect(Collectors.toSet());
        }
        if (!tagsIdExistent.isEmpty()) {
            insertedIds.addAll(tagsIdExistent);
        }
        return insertedIds;
    }
}