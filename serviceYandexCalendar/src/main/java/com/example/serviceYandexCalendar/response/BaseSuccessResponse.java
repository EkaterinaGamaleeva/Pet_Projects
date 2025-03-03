package com.example.serviceYandexCalendar.response;

import lombok.Data;

@Data
public class BaseSuccessResponse<T> {
    private final T data;
}
