package com.dunice.projectNews.repository;

import com.dunice.projectNews.dto.response.GetNewsOutResponse;
import com.dunice.projectNews.dto.response.TagResponse;
import com.dunice.projectNews.exception.CustomException;
import com.dunice.projectNews.mapper.NewsMapper;
import com.dunice.projectNews.mapper.TagMapper;
import com.dunice.projectNews.models.News;
import com.dunice.projectNews.models.Tables;
import com.dunice.projectNews.models.Tag;
import com.dunice.projectNews.models.User;
import com.dunice.projectNews.models.tables.records.NewsRecord;
import com.dunice.projectNews.util.ServerErrorCodes;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static com.dunice.projectNews.models.tables.News.NEWS;
import static com.dunice.projectNews.models.tables.NewsTags.NEWS_TAGS;
import static com.dunice.projectNews.models.tables.Tags.TAGS;
import static com.dunice.projectNews.models.tables.Users.USERS;
import static org.jooq.impl.DSL.selectFrom;
import org.jooq.Condition;

@Repository
@RequiredArgsConstructor
public class NewsRepositoryImpl implements NewsRepository {

    private final DSLContext dsl;

    private final NewsMapper newsMapper;

    private final TagMapper tagMapper;

    private final UserRepository userRepository;

    public void saveNews(News news, Set<Long> tagId) {
        Long newsId = dsl.insertInto(NEWS)
                .set(NEWS.DESCRIPTION, news.getDescription())
                .set(NEWS.IMAGE, news.getImage())
                .set(NEWS.TITLE, news.getTitle())
                .set(NEWS.USERS_ID, news.getAuthor().getUserId())
                .returning(NEWS.NEWS_ID)
                .fetchOne()
                .getValue(NEWS.NEWS_ID);
        addTagsToNews(newsId, tagId);
    }

    public List<GetNewsOutResponse> getPaginatedNews(int page, int perPage) {
        Map<Tag, News> mapTagToNews = new HashMap<>();

        int offset = (page - 1) * perPage;

        Map<UUID, News> userIds = dsl.selectFrom(NEWS)
                .offset(offset)
                .limit(perPage)
                .fetch()
                .intoMap(NEWS.USERS_ID, record -> record.into(News.class));
        Map<User, News> mapUserToNews = getUserToNews(userIds);


        Collection<News> newsList = userIds.values();

        Set<Long> newsIdSet = userIds.values().stream().map(News::getNewsId).collect(Collectors.toSet());

//        for (Long newsId : newsIdSet) {
//            Map<Tag, Long> mapTag = getTagsToNews(newsId);
//            mapTag.entrySet().stream().forEach(entry -> {
//                Tag tag = entry.getKey();
//                Long nId = entry.getValue();
//                News news = newsList.stream().filter(e -> e.getNewsId().equals(nId)).findFirst().get();
//                mapTagToNews.put(tag, news);
//            });
//        }
        return getNewsOutResponses(mapUserToNews, mapTagToNews);
    }

    public News getNewsById(Long id) {
        NewsRecord newsRecord = dsl.selectFrom(NEWS)
                .where(Tables.NEWS.NEWS_ID.eq(id))
                .fetchOptional()
                .orElseThrow(() -> new CustomException(ServerErrorCodes.NEWS_NOT_FOUND));
        User user = getUserById(newsRecord.getUsersId());
        News news = newsMapper.getNewsByNewsRecord(newsRecord);
        news.setAuthor(user);
        return news;
    }

    @Override
    public List<GetNewsOutResponse> findAllNewsDinamicWhere(String author, String keywords, Set<String> tags, Integer page, Integer perPage) {
        int offset = (page - 1) * perPage;


        List<Condition> conditions = new ArrayList<>();

        if (author != null && !author.isEmpty()) {
            conditions.add(Tables.NEWS.USERS_ID.eq(UUID.fromString(author)));
        }

        if (keywords != null && !keywords.isEmpty()) {
            String likeExpression = "%" + keywords + "%";
            conditions.add(Tables.NEWS.TITLE.likeIgnoreCase(likeExpression)
                    .or(Tables.NEWS.DESCRIPTION.likeIgnoreCase(likeExpression)));
        }

        if (tags != null && !tags.isEmpty()) {
            conditions.add(Tables.NEWS.NEWS_ID.in(
                    dsl.select(Tables.NEWS_TAGS.NEWS_ID)
                            .from(Tables.NEWS_TAGS)
                            .where(Tables.NEWS_TAGS.TAG_ID.in(
                                    dsl.select(Tables.TAGS.TAG_ID)
                                            .from(Tables.TAGS)
                                            .where(Tables.TAGS.TITLE.in(tags))
                            ))
            ));

        }
        return dsl.selectFrom(Tables.NEWS)
                .where(conditions)
                .orderBy(Tables.NEWS.NEWS_ID.desc()) // Сортировка по дате создания
                .offset(offset)
                .limit(perPage)
                .fetchInto(News.class)
                .stream()
                .map(newsMapper::newsToGetNewsDtoOut)
                .toList();
    }


    public List<GetNewsOutResponse> getNewsByUserID(UUID id, int page, int perPage) {
        Map<Tag, News> mapTagToNews = new HashMap<>();

        int offset = (page - 1) * perPage;

        Map<UUID, News> userIds = dsl.selectFrom(NEWS)
                .where(NEWS.USERS_ID.eq(id))
                .offset(offset)
                .limit(perPage)
                .fetch()
                .intoMap(NEWS.USERS_ID, record -> record.into(News.class));
        Map<User, News> mapUserToNews = getUserToNews(userIds);


        Collection<News> newsList = userIds.values();

        Set<Long> newsIdSet = userIds.values().stream().map(News::getNewsId).collect(Collectors.toSet());

        for (Long newsId : newsIdSet) {
            Map<Tag, Long> mapTag = getTagsToNews(newsId);
            mapTag.entrySet().stream().forEach(entry -> {
                Tag tag = entry.getKey();
                Long nId = entry.getValue();
                News news = newsList.stream().filter(e -> e.getNewsId().equals(nId)).findFirst().get();
                mapTagToNews.put(tag, news);
            });
        }
        return getNewsOutResponses(mapUserToNews, mapTagToNews);
    }

    public User getUserById(UUID id) {
        return userRepository.getUserById(id);
    }

    public void deleteById(Long id) {
        Set<Long> tadId = new HashSet<>();
        tadId = dsl.deleteFrom(NEWS_TAGS)
                .where(NEWS_TAGS.NEWS_ID.eq(id))
                .returning().fetchSet(NEWS_TAGS.TAG_ID);
        dsl.deleteFrom(TAGS)
                .where(TAGS.TAG_ID.in(tadId).andNotExists(selectFrom(NEWS_TAGS).where(NEWS_TAGS.TAG_ID.eq(TAGS.TAG_ID))))
                .execute();
        dsl.deleteFrom(NEWS)
                .where(NEWS.NEWS_ID.eq(id))
                .execute();
    }

    public void putNews(News news, Set<Long> tagId) {
        deleteById(news.getNewsId());
        Long newsId = dsl.insertInto(NEWS)
                .set(NEWS.NEWS_ID, news.getNewsId())
                .set(NEWS.DESCRIPTION, news.getDescription())
                .set(NEWS.IMAGE, news.getImage())
                .set(NEWS.TITLE, news.getTitle())
                .set(NEWS.USERS_ID, news.getAuthor().getUserId())
                .returning(NEWS.NEWS_ID)
                .fetchOne()
                .getValue(NEWS.NEWS_ID);
        addTagsToNews(newsId, tagId);
    }

    public void addTagsToNews(Long newsId, Set<Long> tagIds) {
        tagIds.forEach(tagId ->
                dsl.insertInto(NEWS_TAGS)
                        .set(NEWS_TAGS.NEWS_ID, newsId)
                        .set(NEWS_TAGS.TAG_ID, tagId)
                        .onDuplicateKeyIgnore()
                        .execute()
        );
    }


    public List<GetNewsOutResponse> getNewsOutResponses(Map<User, News> userMap, Map<Tag, News> tagMap) {
        List<GetNewsOutResponse> result = new ArrayList<>();

        userMap.forEach((user, news) -> {
            GetNewsOutResponse getNewsOutResponse = newsMapper.newsToGetNewsDtoOut(news);
            getNewsOutResponse.setUserId(user.getUserId().toString());
            getNewsOutResponse.setUsername(user.getEmail());
            result.add(getNewsOutResponse);
        });

        result.forEach(news -> {
            Set<TagResponse> newTagSet = tagMap.entrySet().stream()
                    .filter(entry -> entry.getValue().getNewsId().equals(news.getId()))
                    .map(e -> {
                        return tagMapper.tagToTagDto(e.getKey());
                    })
                    .collect(Collectors.toSet());
            news.setTags(newTagSet);
        });

        return result;
    }

    public Map<Tag, Long> getTagsToNews(Long newsId) {

        List<Long> tagIds = dsl.select(NEWS_TAGS.TAG_ID)
                .from(NEWS_TAGS)
                .where(NEWS_TAGS.NEWS_ID.eq(newsId))
                .fetch(NEWS_TAGS.TAG_ID);


        List<Tag> tags = dsl.selectFrom(TAGS)
                .where(TAGS.TAG_ID.in(tagIds))
                .fetchInto(Tag.class);

        return tags.stream()
                .collect(Collectors.toMap(tag -> tag, tag -> newsId));

    }

    public Map<User, News> getUserToNews(Map<UUID, News> userNewsMap) {
        Set<UUID> userIds = userNewsMap.keySet();
        Map<User, News> newsUserNewsMap = new HashMap<>();
        List<User> users = dsl.selectFrom(USERS)
                .where(USERS.USERS_ID.in(userIds))
                .fetchInto(User.class);

        userNewsMap.entrySet().stream().forEach(entry -> {
            UUID userId = entry.getKey();
            News news = entry.getValue();
            User user = users.stream().filter(e -> e.getUserId().equals(userId)).findFirst().get();
            newsUserNewsMap.put(user, news);
        });

        return newsUserNewsMap;

    }
}
