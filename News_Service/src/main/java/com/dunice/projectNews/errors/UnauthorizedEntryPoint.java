package com.dunice.projectNews.errors;

import com.dunice.projectNews.dto.common.BaseSuccessResponse;
import com.dunice.projectNews.util.AppConstants;
import com.dunice.projectNews.util.ServerErrorCodes;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.io.IOException;

public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        BaseSuccessResponse baseSuccessResponse =
                new BaseSuccessResponse(ServerErrorCodes.map
                        .get(ServerErrorCodes.UNAUTHORISED.getMessage()), true);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(AppConstants.TYPE_APPLICATION_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(baseSuccessResponse));
        response.getWriter().flush();
    }
}
