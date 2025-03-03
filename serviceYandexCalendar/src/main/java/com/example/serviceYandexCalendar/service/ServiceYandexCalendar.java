package com.example.serviceYandexCalendar.service;

import com.github.caldav4j.exceptions.CalDAV4JException;

import java.time.LocalDateTime;

public interface ServiceYandexCalendar {
    public void sendServiceYandexCalendar(LocalDateTime startDateTime, LocalDateTime endDateTime, String name, String description) throws CalDAV4JException;
}
