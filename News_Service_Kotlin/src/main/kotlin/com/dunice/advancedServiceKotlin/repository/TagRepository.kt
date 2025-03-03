package com.dunice.advancedServiceKotlin.repository

import com.dunice.advancedServiceKotlin.models.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*
@Repository
interface TagRepository : JpaRepository<Tag, Long> {
    fun findByTitle(title: String?): Optional<Tag>?
}
