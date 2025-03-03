package com.example.serviceYandexCalendar.errors;

import com.example.serviceYandexCalendar.exception.CustomException;
import com.example.serviceYandexCalendar.response.BaseSuccessResponse;
import com.example.serviceYandexCalendar.util.FileErrorCodesAndMessage;
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
                new BaseSuccessResponse<>(FileErrorCodesAndMessage.mapErrors.get(e.getFileErrorCodesAndMessage()));
        System.out.println(baseSuccessResponse);
        return ResponseEntity.badRequest().body(baseSuccessResponse);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<BaseSuccessResponse<String>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        System.out.println(new BaseSuccessResponse<>(FileErrorCodesAndMessage.mapErrors.get(FileErrorCodesAndMessage.FILE_MAX_SIZE)));
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(new BaseSuccessResponse<>(FileErrorCodesAndMessage.mapErrors.get(FileErrorCodesAndMessage.FILE_MAX_SIZE)));
    }

}
