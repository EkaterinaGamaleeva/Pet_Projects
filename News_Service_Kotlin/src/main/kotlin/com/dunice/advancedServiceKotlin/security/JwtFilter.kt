package com.dunice.advancedServiceKotlin.security

import com.dunice.advancedServiceKotlin.util.AppConstants
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtFilter @Autowired constructor(
    private val jwtUserService: JwtUserService,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val autnHandler = request.getHeader(AppConstants.HEADER_AUTHORIZATION)
        val token: String
        if (autnHandler != null && !autnHandler.isBlank() && autnHandler.startsWith(AppConstants.TOKEN_BEARER)) {
            token = autnHandler.substring(7)
        } else {
            filterChain.doFilter(request, response)
            return
        }
        if (jwtUserService.validateToken(token) && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(jwtUserService.getEmailByToken(token))
            val authToken =
                UsernamePasswordAuthenticationToken(
                    userDetails,
                    userDetails!!.password,
                    userDetails.authorities
                )
            SecurityContextHolder.getContext().authentication = authToken
        }
        filterChain.doFilter(request, response)
    }
}

