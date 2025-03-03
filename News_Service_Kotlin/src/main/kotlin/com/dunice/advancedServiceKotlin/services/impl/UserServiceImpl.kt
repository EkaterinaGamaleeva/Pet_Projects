package com.dunice.advancedServiceKotlin.services.impl

import News
import com.dunice.advancedServiceKotlin.dto.request.PutUserRequest
import com.dunice.advancedServiceKotlin.dto.response.PublicUserResponse
import com.dunice.advancedServiceKotlin.exception.CustomException
import com.dunice.advancedServiceKotlin.mapper.UserMapper
import com.dunice.advancedServiceKotlin.models.User
import com.dunice.advancedServiceKotlin.repository.UserRepository
import com.dunice.advancedServiceKotlin.services.UserService
import com.dunice.advancedServiceKotlin.util.ServerErrorCodes
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl @Autowired constructor(val userRepository: UserRepository, private val userMapper: UserMapper) :
    UserService {
    override val allUser: List<PublicUserResponse?>?
        get() = userMapper.listUserToListPublicUserResponse(userRepository.findAll().filterNotNull())

    override fun replaceUser(publicUserView: PutUserRequest?): PublicUserResponse? {
        val user = userRepository
            .findByEmail(SecurityContextHolder.getContext().authentication.name)
            ?: throw CustomException(ServerErrorCodes.USER_NOT_FOUND)
        val userUpdate = userMapper.putUserRequestToUser(publicUserView  ?: throw CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT))
        userUpdate.userId = user.userId
        userUpdate.password = user.password
        userUpdate.news = user.news
        userRepository.save(userUpdate)
        return userMapper.userToPublicUserResponse(userUpdate)
    }

    @Transactional
    override fun deleteUser() {
        userRepository
            .deleteByEmail(SecurityContextHolder.getContext().authentication.name)
    }

    override fun getUserInfoById(id: UUID): PublicUserResponse? {
        return userMapper
            .userToPublicUserResponse(
                userRepository
                    .findById(id)
                    .orElseThrow { CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT) }
                    ?: throw CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT)
            )
    }

    override val userInfo: PublicUserResponse?
        get() = userMapper
            .userToPublicUserResponse(
                userRepository
                    .findByEmail(SecurityContextHolder.getContext().authentication.name)
                    ?: throw CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT)
            )

    override fun saveUserForNews(news: News) {
        val user =
            userRepository
                .findByEmail(SecurityContextHolder.getContext().authentication.name)
                ?: throw CustomException(ServerErrorCodes.USER_NOT_FOUND)
        news.author = user
    }

    override fun checkingUserCurrentAuthenticationUser(user: User): Boolean {
        val userAuthentication =
            userRepository
                .findByEmail(SecurityContextHolder.getContext().authentication.name)
                ?: throw CustomException(ServerErrorCodes.USER_NOT_FOUND)
        return user.email == userAuthentication.email
    }
}
