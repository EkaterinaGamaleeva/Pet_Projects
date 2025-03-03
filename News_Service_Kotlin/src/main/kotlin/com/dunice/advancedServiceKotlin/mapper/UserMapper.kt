package com.dunice.advancedServiceKotlin.mapper

import com.dunice.advancedServiceKotlin.dto.request.PutUserRequest
import com.dunice.advancedServiceKotlin.dto.request.RegisterUserRequest
import com.dunice.advancedServiceKotlin.dto.response.LoginUserResponse
import com.dunice.advancedServiceKotlin.dto.response.PublicUserResponse
import com.dunice.advancedServiceKotlin.models.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface UserMapper {

    fun registerUserRequestToUser(registerUserDtoRequest: RegisterUserRequest): User

    @Mapping(target = "id", source = "userId")
    fun userToLoginUserResponse(user: User): LoginUserResponse

    fun listUserToListPublicUserResponse(user: List<User>): List<PublicUserResponse>

    @Mapping(target = "id", source = "userId")
    fun userToPublicUserResponse(user: User): PublicUserResponse

    fun putUserRequestToUser(publicUserView: PutUserRequest): User
}
