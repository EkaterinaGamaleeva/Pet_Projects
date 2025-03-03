package com.dunice.projectNews.models;


import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class User {

    private UUID userId;

    private String avatar;

    private String email;

    private String name;

    private String role;

    private String password;

    private Set<News> news;
}
