package com.dunice.projectNews.dto.request;

import com.dunice.projectNews.dto.BaseUserDto;
import com.dunice.projectNews.util.ServerValidationConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest extends BaseUserDto {

    @NotBlank(message = ServerValidationConstants.USER_PASSWORD_NULL)
    private String password;
}
