package com.dunice.advancedServiceKotlin.security

import com.dunice.advancedServiceKotlin.exception.CustomException
import com.dunice.advancedServiceKotlin.repository.UserRepository
import com.dunice.advancedServiceKotlin.util.ServerErrorCodes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository
            .findByEmail(email)
            ?:throw CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT)

        return User.builder()
            .username(user.email)
            .password(user.password)
            .authorities(setOf(SimpleGrantedAuthority(user.role))).build()
    }
}
