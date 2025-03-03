package com.dunice.projectNews.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Tag {

    private Long tagId;

    private Set<News> news;

    private String title;
}
