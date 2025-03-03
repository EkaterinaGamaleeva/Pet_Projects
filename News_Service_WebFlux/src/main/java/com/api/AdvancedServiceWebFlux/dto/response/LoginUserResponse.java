package com.api.AdvancedServiceWebFlux.dto.response;

import com.api.AdvancedServiceWebFlux.dto.BaseUserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginUserResponse extends BaseUserDto {

    private String token;
}
