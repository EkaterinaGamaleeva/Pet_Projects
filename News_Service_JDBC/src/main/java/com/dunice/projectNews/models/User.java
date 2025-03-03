package com.dunice.projectNews.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "news_schema")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "users_id")
    private UUID userId;

    @Column(name = "avatar", nullable = false)
    private String avatar;

    @Column(name = "email")
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "role")
    private String role;

    @Column(name = "password")
    private String password;

    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<News> news;
}
