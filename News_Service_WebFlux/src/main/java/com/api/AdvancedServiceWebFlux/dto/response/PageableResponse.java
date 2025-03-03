package com.api.AdvancedServiceWebFlux.dto.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PageableResponse<T> {

    private T content;

    private Integer numberOfElements;

    public PageableResponse(T content) {

        this.content = content;
    }
}
