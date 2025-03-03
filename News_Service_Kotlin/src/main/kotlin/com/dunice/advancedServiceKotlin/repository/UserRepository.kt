package com.dunice.advancedServiceKotlin.repository

import com.dunice.advancedServiceKotlin.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*
@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): User

    fun deleteByEmail(email: String)

    fun existsByEmail(email: String): Boolean
}
