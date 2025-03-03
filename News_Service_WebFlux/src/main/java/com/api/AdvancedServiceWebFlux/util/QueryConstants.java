package com.api.AdvancedServiceWebFlux.util;


public class QueryConstants {

    public static final String FIND_ALL = """
        SELECT n.news_id AS news_id, n.description, n.image, n.title,
               u.users_id AS users_id, u.avatar, u.email, u.name, u.role, u.password,
               array_agg(t.tag_id) AS tag_id, array_agg(t.title) AS tagTitles
        FROM news_schema.news n
        LEFT JOIN news_schema.users u ON n.users_id = u.users_id
        LEFT JOIN news_schema.news_tags nt ON nt.news_id = n.news_id
        LEFT JOIN news_schema.tags t ON nt.tag_id = t.tag_id
        GROUP BY n.news_id, u.users_id
        OFFSET :offset LIMIT :size
        """;


    public static final String FIND_BY_ID_NEWS = """
            SELECT n.news_id AS news_id, n.description, n.image, n.title,
            u.users_id AS users_id, u.avatar, u.email, u.name, u.role, u.password,
            array_agg(t.tag_id) AS tag_id, array_agg(t.title) AS tagTitles
            FROM news_schema.news n
            LEFT JOIN news_schema.users  u ON n.users_id = u.users_id
            LEFT JOIN news_schema.news_tags nt ON nt.news_id = n.news_id
            LEFT JOIN news_schema.tags t ON nt.tag_id = t.tag_id
            WHERE n.news_id = :id
            GROUP BY n.news_id, u.users_id
            """;

    public static final String UPDATE_NEWS = """
            UPDATE news_schema.news
            SET description = :description, image = :image, title = :title
            WHERE news_id = :id
            """;

    public static final String INSERT_NEWS = """
            INSERT INTO news_schema.news (description, image, title, users_id)
            VALUES (:description, :image, :title, :users_id)
            RETURNING news_id
            """;

    public static final String FIND_ALL_NEWS_WITH_DETAILS_BY_USER_ID = """
            SELECT n.news_id AS news_id, n.description, n.image, n.title,
            u.users_id AS users_id, u.avatar, u.email, u.name, u.role, u.password,
            array_agg(t.tag_id) AS tag_id, array_agg(t.title) AS tagTitles
            FROM news_schema.news n
            LEFT JOIN news_schema.users  u ON n.users_id = u.users_id
            LEFT JOIN news_schema.news_tags nt ON nt.news_id = n.news_id
            LEFT JOIN news_schema.tags t ON nt.tag_id = t.tag_id
            WHERE u.users_id = :id
            GROUP BY n.news_id, u.users_id
            OFFSET :offset LIMIT :size
            """;

    public static final String FIND_ALL_NEWS_DINAMIC_WHERE = """
            SELECT n.news_id AS news_id, n.description, n.image, n.title,
            u.users_id AS users_id, u.avatar, u.email, u.name, u.role, u.password,
            array_agg(t.tag_id) AS tag_id, array_agg(t.title) AS tagTitles
            FROM news_schema.news n
            LEFT JOIN news_schema.users u ON n.users_id = u.users_id
            LEFT JOIN news_schema.news_tags nt ON nt.news_id = n.news_id
            LEFT JOIN news_schema.tags t ON nt.tag_id = t.tag_id
            WHERE 1=1
            """;
    public static final String AUTHOR_NAME_CONDITION = " AND u.name LIKE :authorName";

    public static final String KEYWORDS_CONDITION = " AND (n.title LIKE :keywords OR n.description LIKE :keywords)";

    public static final String TAGS_CONDITION = " AND t.title = ANY(:tags)";

    public static final String DELETE_TAGS_BY_NEWS_ID = "DELETE FROM news_schema.news_tags WHERE news_id = :news_id";


    public static final String DELETE_NEWS_BY_ID = "DELETE FROM news_schema.news WHERE news_id = :news_id";
}
