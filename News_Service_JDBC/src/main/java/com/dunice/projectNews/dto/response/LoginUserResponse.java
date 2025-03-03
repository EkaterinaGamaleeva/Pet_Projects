package com.dunice.projectNews.dto.response;

import com.dunice.projectNews.dto.BaseUserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginUserResponse extends BaseUserDto {

    private String token;

}
