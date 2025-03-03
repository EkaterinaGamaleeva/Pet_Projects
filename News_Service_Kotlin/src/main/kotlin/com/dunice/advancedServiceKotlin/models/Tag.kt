package com.dunice.advancedServiceKotlin.models

import News
import jakarta.persistence.*

@Entity
@Table(name = "tags", schema = "news_schema")
 class Tag(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", nullable = false)
    var tagId: Long=0L,

    @ManyToMany(mappedBy = "tags")
    var news: Set<News> = HashSet(),

    @Column(name = "title", nullable = false)
    var title: String="",
)
