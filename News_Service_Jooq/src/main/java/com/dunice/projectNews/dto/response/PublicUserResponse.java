package com.dunice.projectNews.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PublicUserResponse {

    private String avatar;

    private String email;

    private String name;

    private String role;

    private UUID id;
}
