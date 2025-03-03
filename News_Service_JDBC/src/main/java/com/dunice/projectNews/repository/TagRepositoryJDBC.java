package com.dunice.projectNews.repository;

import com.dunice.projectNews.models.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class TagRepositoryJDBC {

    private final JdbcTemplate jdbcTemplate;


    public Optional<Tag> findByTitle(String title) {
        try {
            String query = "SELECT tag_id, title FROM tags WHERE title = ?";
            Tag tag = jdbcTemplate.queryForObject(query, new TagRowMapper(), title);
            return Optional.ofNullable(tag);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Set<Tag> saveAll(Set<Tag> tags) {
        String insertTagQuery = "INSERT INTO tags (title) VALUES (?)";
        Set<Tag> savedTags = new HashSet<>();

        for (Tag tag : tags) {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertTagQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, tag.getTitle());
                return ps;
            }, keyHolder);

            tag.setTagId((Long) keyHolder.getKeys().get("tag_id"));
            savedTags.add(tag);
        }
        return savedTags;
    }

    public static class TagRowMapper implements RowMapper<Tag> {
        @Override
        public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
            Tag tag = new Tag();
            tag.setTagId(rs.getLong("tag_id"));
            tag.setTitle(rs.getString("title"));
            return tag;
        }
    }
}