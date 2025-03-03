package com.dunice.projectNews.services;

import com.dunice.projectNews.dto.request.NewsRequest;
import com.dunice.projectNews.models.Tag;
import java.util.Set;

public interface TagService {
    Set<Tag> parseTag(NewsRequest newsRequest);
}
