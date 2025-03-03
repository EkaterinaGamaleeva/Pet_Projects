package com.dunice.projectNews.interceptor;

import com.dunice.projectNews.models.RequestLog;
import com.dunice.projectNews.repository.RequestLogRepository;
import com.dunice.projectNews.util.AppConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class CustomInterceptor implements HandlerInterceptor {

    private final RequestLogRepository requestLogRepository;

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : AppConstants.NO_AUTHORIZATION;
        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        int status = response.getStatus();
        String error = (String) request.getAttribute(AppConstants.ERRORS);

        if (error == null) {
            error = AppConstants.MESSAGE_STATUS_OK;
        }
        RequestLog log = new RequestLog(
                LocalDateTime.now(),
                method,
                url,
                status,
                error,
                username
        );
        requestLogRepository.save(log);
    }
}
