package com.dunice.projectNews.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class News {

    private Long newsId;

    private String description;

    private String image;

    private String title;

    private Set<Tag> tags;

    private User author;
}
