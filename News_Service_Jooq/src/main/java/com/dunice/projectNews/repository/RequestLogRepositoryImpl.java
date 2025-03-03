package com.dunice.projectNews.repository;

import com.dunice.projectNews.models.RequestLog;
import com.dunice.projectNews.models.User;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.dunice.projectNews.models.Tables.REQUEST_LOGS;
import static com.dunice.projectNews.models.Tables.USERS;
@Repository
@RequiredArgsConstructor
public class RequestLogRepositoryImpl implements RequestLogRepository{

    private final DSLContext dsl;

    @Override
    public void save(RequestLog log) {
            dsl.insertInto(REQUEST_LOGS)
                    .set(REQUEST_LOGS.USERS_EMAIL, log.getUser())
                    .set(REQUEST_LOGS.ERRORS, log.getErrors())
                    .set(REQUEST_LOGS.METHOD, log.getMethod())
                    .set(REQUEST_LOGS.URL, log.getUrl())
                    .set(REQUEST_LOGS.STATUS,log.getStatus())
                    .set(REQUEST_LOGS.TIMESTAMP, log.getTimestamp())
                    .execute();
    }
}
