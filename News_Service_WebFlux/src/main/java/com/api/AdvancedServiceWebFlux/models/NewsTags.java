package com.api.AdvancedServiceWebFlux.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsTags {

    private Long newsId;

    private Long tagId;

}
