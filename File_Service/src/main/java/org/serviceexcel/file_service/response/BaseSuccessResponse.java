package org.serviceexcel.file_service.response;

import lombok.Data;

@Data
public class BaseSuccessResponse <T> {
    private final T data;
}
