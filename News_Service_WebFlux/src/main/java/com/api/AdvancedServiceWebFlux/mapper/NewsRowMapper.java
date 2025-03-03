package com.api.AdvancedServiceWebFlux.mapper;

import com.api.AdvancedServiceWebFlux.models.News;
import com.api.AdvancedServiceWebFlux.models.Tag;
import com.api.AdvancedServiceWebFlux.models.User;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class NewsRowMapper {

    public News mapRow(Row row, RowMetadata metadata) {
        News news = new News();

        news.setNewsId(row.get("news_id", Long.class));
        news.setDescription(row.get("description", String.class));
        news.setImage(row.get("image", String.class));
        news.setTitle(row.get("title", String.class));

        User user = new User();
        user.setUserId(row.get("users_id", UUID.class));
        user.setAvatar(row.get("avatar", String.class));
        user.setEmail(row.get("email", String.class));
        user.setName(row.get("name", String.class));
        user.setRole(row.get("role", String.class));
        news.setAuthor(user);

        Long[] tagId = row.get("tag_id", Long[].class);
        String[] tagTitles = row.get("tagTitles", String[].class);
        assert tagId != null;

        if (tagId != null && tagId.length > 0 && tagTitles != null && tagTitles.length > 0) {
            Set<Tag> tags = new HashSet<>();
            for (int i = 0; i < tagId.length; i++) {
                Tag tag = new Tag();
                tag.setTagId(tagId[i]);
                tag.setTitle(tagTitles[i]);
                tags.add(tag);
            }
            news.setTags(tags);
        }

        return news;
    }
}
