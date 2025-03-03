package com.api.AdvancedServiceWebFlux.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomSuccessResponse<T> extends BaseSuccessResponse {

    private final T data;

    public CustomSuccessResponse(T data) {
        this.data = data;
    }

    public CustomSuccessResponse(int statusCode, T data) {
        super(statusCode);
        this.data = data;
    }
}
