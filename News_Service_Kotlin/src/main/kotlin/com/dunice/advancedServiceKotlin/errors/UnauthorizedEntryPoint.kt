package com.dunice.advancedServiceKotlin.errors

import com.dunice.advancedServiceKotlin.dto.common.BaseSuccessResponse
import com.dunice.advancedServiceKotlin.util.AppConstants
import com.dunice.advancedServiceKotlin.util.ServerErrorCodes
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.io.IOException

class UnauthorizedEntryPoint : AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val baseSuccessResponse =
            BaseSuccessResponse(
                ServerErrorCodes.map[ServerErrorCodes.UNAUTHORISED.message]!!, true
            )
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = AppConstants.TYPE_APPLICATION_JSON
        val objectMapper = ObjectMapper()
        response.writer.write(objectMapper.writeValueAsString(baseSuccessResponse))
        response.writer.flush()
    }
}
