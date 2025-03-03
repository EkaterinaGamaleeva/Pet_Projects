package com.api.serviceYandexMail.errors;

import com.api.serviceYandexMail.dto.response.BaseSuccessResponse;
import com.api.serviceYandexMail.exception.CustomException;
import com.api.serviceYandexMail.util.ErrorsCodesAndMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseSuccessResponse<String>> handleException(CustomException e, HttpServletRequest request) {
        BaseSuccessResponse<String> baseSuccessResponse =
                new BaseSuccessResponse<>(ErrorsCodesAndMessage.mapErrors.get(e.getErrorsCodesAndMessage()));
        return ResponseEntity.badRequest().body(baseSuccessResponse);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<BaseSuccessResponse<String>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(new BaseSuccessResponse<>(ErrorsCodesAndMessage.mapErrors.get(ErrorsCodesAndMessage.FILE_MAX_SIZE)));
    }
}
