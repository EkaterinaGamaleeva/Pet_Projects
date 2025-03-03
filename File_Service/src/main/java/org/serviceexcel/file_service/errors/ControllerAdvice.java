package org.serviceexcel.file_service.errors;


import jakarta.servlet.http.HttpServletRequest;
import org.serviceexcel.file_service.exception.CustomException;
import org.serviceexcel.file_service.response.BaseSuccessResponse;
import org.serviceexcel.file_service.util.FileErrorCodesAndMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseSuccessResponse<String>> handleException(CustomException e, HttpServletRequest request) {
        BaseSuccessResponse<String> baseSuccessResponse =
                new BaseSuccessResponse<>(FileErrorCodesAndMessage.mapErrors.get(e.getFileErrorCodesAndMessage()));
        return ResponseEntity.badRequest().body(baseSuccessResponse);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<BaseSuccessResponse<String>> handleException(MultipartException e, HttpServletRequest request) {
        BaseSuccessResponse<String> baseSuccessResponse =
                new BaseSuccessResponse<>(FileErrorCodesAndMessage.mapErrors.get(FileErrorCodesAndMessage.FILE_IS_EMPTY));
        return ResponseEntity.badRequest().body(baseSuccessResponse);
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<BaseSuccessResponse<String>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(new BaseSuccessResponse<>(FileErrorCodesAndMessage.mapErrors.get(FileErrorCodesAndMessage.FILE_MAX_SIZE)));
    }
}
