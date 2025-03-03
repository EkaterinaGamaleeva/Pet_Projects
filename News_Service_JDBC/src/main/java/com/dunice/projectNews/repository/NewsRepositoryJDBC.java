package com.dunice.projectNews.repository;

import com.dunice.projectNews.exception.CustomException;
import com.dunice.projectNews.models.News;
import com.dunice.projectNews.models.Tag;
import com.dunice.projectNews.util.ServerErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class NewsRepositoryJDBC {
    private final JdbcTemplate jdbcTemplate;
    private final UserRepositoryJDBC userRepository;

    public void saveNews(News news) {
        String insertNewsQuery = "INSERT INTO news (title, image, users_id, description) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertNewsQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, news.getTitle());
            ps.setString(2, news.getImage());
            ps.setObject(3, news.getAuthor().getUserId(), java.sql.Types.OTHER);
            ps.setString(4, news.getDescription());
            return ps;
        }, keyHolder);

        Long newsId = (Long) keyHolder.getKeys().get("news_id");

        List<Object[]> batchArgs = new ArrayList<>();
        for (Tag tag : news.getTags()) {
            batchArgs.add(new Object[]{newsId, tag.getTagId()});
        }
        String insertNewsTagQuery = "INSERT INTO news_tags (news_id, tag_id) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(insertNewsTagQuery, batchArgs);
    }

    public Optional<News> findById(Long id) {
        String selectNewsQuery = "SELECT * FROM news WHERE news_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(selectNewsQuery, this::mapRowToNews, id));
    }

    public List<News> findAll(Integer page, Integer perPage) {
        int offset = (page - 1) * perPage;
        int limit = perPage;
        String selectNewsQuery = "SELECT * FROM news ORDER BY news_id DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(selectNewsQuery, this::mapRowToNews, new Object[]{limit, offset});
    }

    private News mapRowToNews(ResultSet rs, int rowNum) throws SQLException {
        News news = new News();
        news.setNewsId(rs.getLong("news_id"));
        news.setTitle(rs.getString("title"));
        news.setImage(rs.getString("image"));
        news.setDescription(rs.getString("description"));
        news.setAuthor(userRepository.findById(rs.getObject("users_id", UUID.class)).orElseThrow(() -> new CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT)));
        news.setTags(findTagsByNewsId(rs.getLong("news_id")));
        return news;

    }

    public Set<Tag> findTagsByNewsId(Long newsId) {
        String selectTagsQuery = "SELECT t.* FROM tags t JOIN news_tags nt ON t.tag_id = nt.tag_id WHERE nt.news_id = ?";
        List<Tag> tagList = jdbcTemplate.query(selectTagsQuery, new TagRepositoryJDBC.TagRowMapper(), newsId);
        return new HashSet<>(tagList);
    }

    public void deleteNewsById(Long id) {
        String deleteQuery = "DELETE FROM news WHERE news_id = ?";
        jdbcTemplate.update(deleteQuery, id);
    }

    public List<News> findAllNewsDinamicWhere(Integer page,
                                              Integer perPage,
                                              String author,
                                              String keywords,
                                              Set<String> tags) {
        int offset = (page - 1) * perPage;
        int limit = perPage;


        StringBuilder sql = new StringBuilder("SELECT n.*");
        sql.append("FROM news n ")
                .append("JOIN users u ON n.users_id = u.users_id ")
                .append("LEFT JOIN news_tags nt ON n.news_id = nt.news_id ")
                .append("LEFT JOIN tags t ON nt.tag_id = t.tag_id ")
                .append("WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (author != null && !author.isEmpty()) {
            sql.append("AND u.email LIKE ? ");
            params.add("%" + author + "%");
        }

        if (keywords != null && !keywords.isEmpty()) {
            sql.append("AND (n.title LIKE ? OR n.description LIKE ?) ");
            params.add("%" + keywords + "%");
            params.add("%" + keywords + "%");
        }

        if (tags != null && !tags.isEmpty()) {
            sql.append("AND t.title IN (");
            for (String tag : tags) {
                sql.append("?,");
                params.add(tag);
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(") ");
        }
        sql.append("LIMIT ? OFFSET ? ");
        params.add(limit);
        params.add(offset);

        return jdbcTemplate.query(sql.toString(), params.toArray(), this::mapRowToNews);
    }

    public List<News> findAllNewsWithDetailsByUserId(UUID id, Integer page, Integer perPage) {
        int offset = (page - 1) * perPage;
        int limit = perPage;
        String selectNewsQuery = "SELECT n.* FROM news n " +
                "INNER JOIN users u ON n.users_id = u.users_id " +
                "WHERE (? IS NULL OR u.users_id = ?) " +
                "ORDER BY n.news_id " +
                "LIMIT ? OFFSET ?";
        return jdbcTemplate.query(selectNewsQuery, this::mapRowToNews, id, id, limit, offset);
    }
}