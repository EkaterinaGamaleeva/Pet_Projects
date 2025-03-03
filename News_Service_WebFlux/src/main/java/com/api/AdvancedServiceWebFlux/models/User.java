package com.api.AdvancedServiceWebFlux.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;
import java.util.UUID;


@Table("news_schema.users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @Column("users_id")
    private UUID userId;

    private String avatar;

    private String email;

    private String name;

    private String role;

    private String password;

    @Transient
    private Set<News> news;
}
