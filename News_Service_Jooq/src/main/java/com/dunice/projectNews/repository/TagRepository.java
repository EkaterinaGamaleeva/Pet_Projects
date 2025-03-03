package com.dunice.projectNews.repository;

import com.dunice.projectNews.dto.request.NewsRequest;
import com.dunice.projectNews.models.Tag;

import java.util.Set;

public interface TagRepository {
    Set<Long> saveNewsWithTags(NewsRequest newsRequest);

}
