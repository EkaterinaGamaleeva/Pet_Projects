package com.dunice.projectNews.services.impl;

import com.dunice.projectNews.dto.request.NewsRequest;
import com.dunice.projectNews.models.Tag;
import com.dunice.projectNews.repository.TagRepository;
import com.dunice.projectNews.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Transactional
    public Set<Long> parseTag(NewsRequest newsRequest) {

        return tagRepository.saveNewsWithTags(newsRequest);
    }

}
