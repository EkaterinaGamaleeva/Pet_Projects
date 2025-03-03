package com.dunice.advancedServiceKotlin.services

import News
import com.dunice.advancedServiceKotlin.dto.request.PutUserRequest
import com.dunice.advancedServiceKotlin.dto.response.PublicUserResponse
import com.dunice.advancedServiceKotlin.models.User
import java.util.*

interface UserService {

    val allUser: List<PublicUserResponse?>?

    fun replaceUser(publicUserView: PutUserRequest?): PublicUserResponse?

    fun deleteUser()

    fun getUserInfoById(id: UUID): PublicUserResponse?

    val userInfo: PublicUserResponse?

    fun saveUserForNews(news: News)

    fun checkingUserCurrentAuthenticationUser(user: User): Boolean
}
