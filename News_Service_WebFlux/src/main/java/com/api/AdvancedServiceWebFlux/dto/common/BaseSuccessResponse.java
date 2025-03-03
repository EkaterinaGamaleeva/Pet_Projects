package com.api.AdvancedServiceWebFlux.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class BaseSuccessResponse {

    private final Integer statusCode;

    private final Boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Integer> codes;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    public BaseSuccessResponse() {
        statusCode = 0;
        success = true;
    }

    public BaseSuccessResponse(int statusCode) {
        this.statusCode = statusCode;
        success = true;
    }

    public BaseSuccessResponse(int statusCode, boolean success) {
        this.statusCode = statusCode;
        this.success = success;
    }
}
