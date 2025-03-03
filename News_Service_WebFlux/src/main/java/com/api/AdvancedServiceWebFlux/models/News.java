package com.api.AdvancedServiceWebFlux.models;

import com.api.AdvancedServiceWebFlux.security.CustomUserDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table("news_schema.news")
@Getter
@Setter
@NoArgsConstructor
public class News {

    @Id
    @Column("news_id")
    private Long newsId;

    private String description;

    private String image;

    private String title;

    @Transient
    private Set<Tag> tags;

    @Column("users_id")
    private User author;

}
