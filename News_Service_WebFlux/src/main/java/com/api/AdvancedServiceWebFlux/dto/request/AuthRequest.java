package com.api.AdvancedServiceWebFlux.dto.request;

import com.api.AdvancedServiceWebFlux.util.AppConstants;
import com.api.AdvancedServiceWebFlux.util.ServerValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @Pattern(regexp = AppConstants.PATTERN_FORMAT_EMAIL, message = ServerValidationConstants.USER_EMAIL_NOT_VALID)
    @Size(min = 3, max = 100, message = ServerValidationConstants.EMAIL_SIZE_NOT_VALID)
    @NotBlank(message = ServerValidationConstants.USER_EMAIL_NOT_VALID)
    private String email;

    @NotBlank(message = ServerValidationConstants.PASSWORD_NOT_VALID)
    private String password;
}
