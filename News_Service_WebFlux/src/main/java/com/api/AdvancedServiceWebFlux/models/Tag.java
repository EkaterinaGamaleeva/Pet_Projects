package com.api.AdvancedServiceWebFlux.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Setter
@Getter
@Table("news_schema.tags")
@NoArgsConstructor
public class Tag {

    @Id
    @Column("tag_id")
    private Long tagId;

    @Transient
    private Set<News> news;

    private String title;
}
