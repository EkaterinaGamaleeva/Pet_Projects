package com.dunice.projectNews.dto.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
