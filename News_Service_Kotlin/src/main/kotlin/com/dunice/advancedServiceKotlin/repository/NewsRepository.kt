package com.dunice.advancedServiceKotlin.repository

import News
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*
@Repository
interface NewsRepository : JpaRepository<News, Long> {
    @Query(
        """
                SELECT n
                FROM News n
                INNER JOIN FETCH n.author u
                LEFT JOIN FETCH n.tags t
                WHERE (:id IS NULL OR u.userId = :id)
                ORDER BY n.newsId
            
            """
    )
    fun findAllNewsWithDetailsByUserId(@Param("id") id: UUID, pageable: Pageable): Page<News>

    @Query(
        "SELECT n FROM News n " +
                "LEFT JOIN n.tags t " +
                "LEFT JOIN n.author a " +
                "WHERE (:authorName IS NULL OR a.name LIKE %:authorName%) " +
                "AND (:keywords IS NULL OR n.title LIKE %:keywords% OR n.description LIKE %:keywords%) " +
                "AND (:tags IS NULL OR t.title IN :tags) "
    )
    fun findAllNewsDinamicWhere(
        @Param("authorName") authorName: String,
        @Param("keywords") keywords: String,
        @Param("tags") tags: Set<String>,
        pageable: Pageable
    ): Page<News>
}
