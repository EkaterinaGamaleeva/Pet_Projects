package com.api.AdvancedServiceWebFlux.dto.request;

import com.api.AdvancedServiceWebFlux.dto.BaseUserDto;
import com.api.AdvancedServiceWebFlux.util.ServerValidationConstants;
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
