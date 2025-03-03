package com.api.AdvancedServiceWebFlux.mapper;

import com.api.AdvancedServiceWebFlux.dto.request.PutUserRequest;
import com.api.AdvancedServiceWebFlux.dto.request.RegisterUserRequest;
import com.api.AdvancedServiceWebFlux.dto.response.LoginUserResponse;
import com.api.AdvancedServiceWebFlux.dto.response.PublicUserResponse;
import com.api.AdvancedServiceWebFlux.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User registerUserRequestToUser(RegisterUserRequest registerUserDtoRequest);

    @Mapping(target = "id", source = "userId")
    LoginUserResponse userToLoginUserResponse(User user);

    List<PublicUserResponse> listUserToListPublicUserResponse(List<User> user);

    @Mapping(target = "id", source = "userId")
    PublicUserResponse userToPublicUserResponse(User user);


    User putUserRequestToUser(PutUserRequest publicUserView);
}
