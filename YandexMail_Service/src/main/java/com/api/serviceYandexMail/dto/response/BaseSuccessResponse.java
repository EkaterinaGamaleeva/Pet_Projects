package com.api.serviceYandexMail.dto.response;

import lombok.Data;

@Data
public class BaseSuccessResponse <T> {
    private final T data;
}
