package com.dunice.projectNews.repository;

import com.dunice.projectNews.models.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLogRepository{
    void save(RequestLog log);
}
