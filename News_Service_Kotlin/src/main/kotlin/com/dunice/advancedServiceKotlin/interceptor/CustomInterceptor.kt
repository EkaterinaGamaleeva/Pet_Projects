package com.dunice.advancedServiceKotlin.interceptor

import com.dunice.advancedServiceKotlin.models.RequestLog
import com.dunice.advancedServiceKotlin.repository.RequestLogRepository
import com.dunice.advancedServiceKotlin.util.AppConstants
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.time.LocalDateTime

@Component
class CustomInterceptor(private val requestLogRepository: RequestLogRepository) : HandlerInterceptor {
    @Throws(Exception::class)
    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any, ex: Exception?
    ) {
        val authentication = SecurityContextHolder.getContext().authentication
        val username = if ((authentication != null)) authentication.name else AppConstants.NO_AUTHORIZATION
        val method = request.method
        val url = request.requestURL.toString()
        val status = response.status
        var error = request.getAttribute(AppConstants.ERRORS) as String

        if (error == null) {
            error = AppConstants.MESSAGE_STATUS_OK
        }
        val log = RequestLog(
            LocalDateTime.now(),
            method,
            url,
            status,
            error,
            username
        )
        requestLogRepository.save(log)
    }
}
