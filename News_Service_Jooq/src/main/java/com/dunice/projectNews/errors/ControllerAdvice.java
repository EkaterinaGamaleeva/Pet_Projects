//package com.dunice.projectNews.errors;
//
//import com.dunice.projectNews.dto.common.BaseSuccessResponse;
//import com.dunice.projectNews.exception.CustomException;
//import com.dunice.projectNews.util.AppConstants;
//import com.dunice.projectNews.util.ServerErrorCodes;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.ConstraintViolationException;
//import org.springframework.context.support.DefaultMessageSourceResolvable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.multipart.MultipartException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestControllerAdvice
//public class ControllerAdvice {
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<BaseSuccessResponse> handleException(MethodArgumentNotValidException e, HttpServletRequest request) {
//        List<Integer> result = e.getBindingResult().getAllErrors()
//                .stream()
//                .map(m -> ServerErrorCodes.map.get(m.getDefaultMessage())).toList();
//        request.setAttribute(AppConstants.ERRORS, e
//                .getBindingResult()
//                .getAllErrors()
//                .stream()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .collect(Collectors.joining(" ")));
//        BaseSuccessResponse baseSuccessResponse = new BaseSuccessResponse(result.get(0), true);
//        baseSuccessResponse.setCodes(result);
//        return ResponseEntity.badRequest().body(baseSuccessResponse);
//    }
//
//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<BaseSuccessResponse> handleException(CustomException e, HttpServletRequest request) {
//        BaseSuccessResponse baseSuccessResponse =
//                new BaseSuccessResponse(ServerErrorCodes.map.get(e.getMessage()), true);
//        request.setAttribute(AppConstants.ERRORS, e.getMessage());
//        return ResponseEntity.badRequest().body(baseSuccessResponse);
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<BaseSuccessResponse> handleException(ConstraintViolationException ex, HttpServletRequest request) {
//
//        List<Integer> result = ex.getConstraintViolations()
//                .stream()
//                .map(e -> ServerErrorCodes.map.get(e.getMessage()))
//                .toList();
//        request.setAttribute(AppConstants.ERRORS, ex
//                .getConstraintViolations().stream()
//                .map(ConstraintViolation::getMessage)
//                .collect(Collectors.joining(" ")));
//        return new ResponseEntity<>(new BaseSuccessResponse(result.get(0)), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(MultipartException.class)
//    public ResponseEntity<BaseSuccessResponse> handleException(MultipartException exception, HttpServletRequest request) {
//        BaseSuccessResponse baseSuccessResponse = new BaseSuccessResponse();
//        baseSuccessResponse.setCodes(List.of(ServerErrorCodes.map.get(ServerErrorCodes.UNKNOWN.getMessage())));
//        request.setAttribute(AppConstants.ERRORS, ServerErrorCodes.UNKNOWN.getMessage());
//        return ResponseEntity.badRequest().body(baseSuccessResponse);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<BaseSuccessResponse> handleException(Exception exception, HttpServletRequest request) {
//        BaseSuccessResponse baseSuccessResponse = new BaseSuccessResponse();
//        baseSuccessResponse.setCodes(List.of(ServerErrorCodes.map.get(ServerErrorCodes.UNKNOWN.getMessage())));
//        request.setAttribute(AppConstants.ERRORS, ServerErrorCodes.UNKNOWN.getMessage());
//        return ResponseEntity.badRequest().body(baseSuccessResponse);
//    }
//}
