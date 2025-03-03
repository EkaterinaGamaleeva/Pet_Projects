package com.dunice.advancedServiceKotlin.security

import com.dunice.advancedServiceKotlin.exception.CustomException
import com.dunice.advancedServiceKotlin.util.AppConstants
import com.dunice.advancedServiceKotlin.util.ServerErrorCodes
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.*

@Service
class JwtUserService {
    @Value("\${jwt_secret}")
    private val secret: String? = null

    @Value("\${time}")
    private val expiryDateTime: Int? = null

    fun generateToken(email: String?): String {
        return AppConstants.TOKEN_BEARER + Jwts.builder()
            .setSubject(email)
            .setIssuedAt(generateNewDate())
            .setExpiration(expirationNewDate)
            .signWith(Keys.hmacShaKeyFor(secret!!.toByteArray(StandardCharsets.UTF_8)))
            .compact()
    }

    fun generateNewDate(): Date {
        return Date()
    }

    val expirationNewDate: Date
        get() = Date(generateNewDate().time + expiryDateTime!!)

    fun getClaimsByToken(token: String?): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secret!!.toByteArray(StandardCharsets.UTF_8)))
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun getEmailByToken(token: String?): String {
        return getClaimsByToken(token).subject
    }

    fun validateToken(token: String?): Boolean {
        try {
            getClaimsByToken(token)
            if (getClaimsByToken(token).expiration.before(Date())) {
                throw CustomException(ServerErrorCodes.TOKEN_NOT_PROVIDED)
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }
}
