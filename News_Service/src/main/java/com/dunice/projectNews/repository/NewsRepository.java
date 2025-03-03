package com.dunice.projectNews.repository;

import com.dunice.projectNews.models.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Set;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("""
                SELECT n
                FROM News n
                INNER JOIN FETCH n.author u
                LEFT JOIN FETCH n.tags t
                WHERE (:id IS NULL OR u.userId = :id)
                ORDER BY n.newsId
            """)
    Page<News> findAllNewsWithDetailsByUserId(@Param("id") UUID id, Pageable pageable);

    @Query("SELECT n FROM News n " +
            "LEFT JOIN n.tags t " +
            "LEFT JOIN n.author a " +
            "WHERE (:authorName IS NULL OR a.name LIKE %:authorName%) " +
            "AND (:keywords IS NULL OR n.title LIKE %:keywords% OR n.description LIKE %:keywords%) " +
            "AND (:tags IS NULL OR t.title IN :tags) ")
    Page<News> findAllNewsDinamicWhere(@Param("authorName") String authorName,
                                       @Param("keywords") String keywords,
                                       @Param("tags") Set<String> tags,
                                       Pageable pageable);
}
