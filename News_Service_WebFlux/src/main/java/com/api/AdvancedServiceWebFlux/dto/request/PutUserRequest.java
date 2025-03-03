package com.api.AdvancedServiceWebFlux.dto.request;


import com.api.AdvancedServiceWebFlux.util.ServerValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutUserRequest {

    @NotBlank(message = ServerValidationConstants.USER_AVATAR_NOT_NULL)
    private String avatar;

    @Email(message = ServerValidationConstants.USER_EMAIL_NOT_NULL)
    @Size(min = 3, max = 100, message = ServerValidationConstants.EMAIL_SIZE_NOT_VALID)
    @NotBlank(message = ServerValidationConstants.USER_EMAIL_NOT_NULL)
    private String email;

    @NotBlank(message = ServerValidationConstants.USER_NAME_HAS_TO_BE_PRESENT)
    @Size(min = 3, max = 25, message = ServerValidationConstants.USERNAME_SIZE_NOT_VALID)
    private String name;

    @NotBlank(message = ServerValidationConstants.USER_ROLE_NULL)
    @Size(min = 3, max = 25, message = ServerValidationConstants.ROLE_SIZE_NOT_VALID)
    private String role;

}
