package com.dunice.projectNews.mapper;

import com.dunice.projectNews.dto.request.PutUserRequest;
import com.dunice.projectNews.dto.request.RegisterUserRequest;
import com.dunice.projectNews.dto.response.LoginUserResponse;
import com.dunice.projectNews.dto.response.PublicUserResponse;
import com.dunice.projectNews.models.User;
import com.dunice.projectNews.models.tables.records.UsersRecord;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-16T09:42:27+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User registerUserRequestToUser(RegisterUserRequest registerUserDtoRequest) {
        if ( registerUserDtoRequest == null ) {
            return null;
        }

        User user = new User();

        user.setAvatar( registerUserDtoRequest.getAvatar() );
        user.setEmail( registerUserDtoRequest.getEmail() );
        user.setName( registerUserDtoRequest.getName() );
        user.setRole( registerUserDtoRequest.getRole() );
        user.setPassword( registerUserDtoRequest.getPassword() );

        return user;
    }

    @Override
    public LoginUserResponse userToLoginUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        LoginUserResponse loginUserResponse = new LoginUserResponse();

        if ( user.getUserId() != null ) {
            loginUserResponse.setId( user.getUserId().toString() );
        }
        loginUserResponse.setAvatar( user.getAvatar() );
        loginUserResponse.setEmail( user.getEmail() );
        loginUserResponse.setName( user.getName() );
        loginUserResponse.setRole( user.getRole() );

        return loginUserResponse;
    }

    @Override
    public List<PublicUserResponse> listUserToListPublicUserResponse(List<User> user) {
        if ( user == null ) {
            return null;
        }

        List<PublicUserResponse> list = new ArrayList<PublicUserResponse>( user.size() );
        for ( User user1 : user ) {
            list.add( userToPublicUserResponse( user1 ) );
        }

        return list;
    }

    @Override
    public PublicUserResponse userToPublicUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        PublicUserResponse publicUserResponse = new PublicUserResponse();

        publicUserResponse.setId( user.getUserId() );
        publicUserResponse.setAvatar( user.getAvatar() );
        publicUserResponse.setEmail( user.getEmail() );
        publicUserResponse.setName( user.getName() );
        publicUserResponse.setRole( user.getRole() );

        return publicUserResponse;
    }

    @Override
    public User putUserRequestToUser(PutUserRequest publicUserView) {
        if ( publicUserView == null ) {
            return null;
        }

        User user = new User();

        user.setAvatar( publicUserView.getAvatar() );
        user.setEmail( publicUserView.getEmail() );
        user.setName( publicUserView.getName() );
        user.setRole( publicUserView.getRole() );

        return user;
    }

    @Override
    public User userRecordToUser(UsersRecord userRecord) {
        if ( userRecord == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( userRecord.getUsersId() );
        user.setAvatar( userRecord.getAvatar() );
        user.setEmail( userRecord.getEmail() );
        user.setName( userRecord.getName() );
        user.setRole( userRecord.getRole() );
        user.setPassword( userRecord.getPassword() );

        return user;
    }
}
