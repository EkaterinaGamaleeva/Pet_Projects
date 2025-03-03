package com.dunice.projectNews.mapper;

import com.dunice.projectNews.dto.request.PutUserRequest;
import com.dunice.projectNews.dto.request.RegisterUserRequest;
import com.dunice.projectNews.dto.response.LoginUserResponse;
import com.dunice.projectNews.dto.response.PublicUserResponse;
import com.dunice.projectNews.models.User;
import com.dunice.projectNews.models.tables.records.UsersRecord;
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

    @Mapping(target = "userId", source ="usersId")
    User userRecordToUser(UsersRecord userRecord);
}
