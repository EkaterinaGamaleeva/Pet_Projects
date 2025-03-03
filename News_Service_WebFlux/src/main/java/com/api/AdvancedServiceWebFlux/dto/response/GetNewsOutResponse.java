package com.api.AdvancedServiceWebFlux.dto.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GetNewsOutResponse {

    private String description;

    private Long id;

    private String image;

    private Set<TagResponse> tags;

    private String title;

    private String userId;

    private String username;
}
