package com.dunice.advancedServiceKotlin.models

import News
import jakarta.persistence.*
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.util.*
import kotlin.collections.HashSet

@Entity
@Table(name = "users", schema = "news_schema")
 class User(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "users_id")
    var userId: UUID ,

    @Column(name = "avatar", nullable = false)
    var avatar: String ,

    @Column(name = "email")
    var email: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "role")
    var role: String ,

    @Column(name = "password")
    var password: String,

    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], orphanRemoval = true)
    var news: Set<News> = HashSet(),
)
