package com.dunice.projectNews.repository;

import com.dunice.projectNews.models.RequestLog;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RequestLogRepositoryJDBC {

    private final JdbcTemplate jdbcTemplate;

    public void save(RequestLog requestLog) {
        String insertRequestLogQuery = "INSERT INTO request_logs (timestamp,errors,method, status, url,users_email ) VALUES (?,?, ?, ?,?,?)";
        jdbcTemplate.update(insertRequestLogQuery, requestLog.getTimestamp(),requestLog.getErrors(),requestLog.getMethod(), requestLog.getStatus(), requestLog.getUrl(), requestLog.getUser());
    }
}