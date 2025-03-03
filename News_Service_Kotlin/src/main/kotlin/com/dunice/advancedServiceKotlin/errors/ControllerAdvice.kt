package com.dunice.advancedServiceKotlin.errors

import com.dunice.advancedServiceKotlin.dto.common.BaseSuccessResponse
import com.dunice.advancedServiceKotlin.exception.CustomException
import com.dunice.advancedServiceKotlin.util.AppConstants
import com.dunice.advancedServiceKotlin.util.ServerErrorCodes
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MultipartException
import java.util.List
import java.util.stream.Collectors

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleException(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<BaseSuccessResponse> {

        val result = e.bindingResult.allErrors
            .map { it: ObjectError -> ServerErrorCodes.map[it.defaultMessage] }

        request.setAttribute(
            AppConstants.ERRORS,
            e.bindingResult.allErrors
                .joinToString(" ") { it.defaultMessage ?: "" }
        )

        val baseSuccessResponse = BaseSuccessResponse(result.firstOrNull() ?: 0, true)
        baseSuccessResponse.codes = result

        return ResponseEntity.badRequest().body(baseSuccessResponse)
    }


    @ExceptionHandler(CustomException::class)
    fun handleException(e: CustomException, request: HttpServletRequest): ResponseEntity<BaseSuccessResponse> {
        val baseSuccessResponse =
            BaseSuccessResponse(ServerErrorCodes.map[e.message]!!, true)
        request.setAttribute(AppConstants.ERRORS, e.message)
        return ResponseEntity.badRequest().body(baseSuccessResponse)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleException(
        ex: ConstraintViolationException,
        request: HttpServletRequest
    ): ResponseEntity<BaseSuccessResponse> {
        val result = ex.constraintViolations
            .stream()
            .map { e: ConstraintViolation<*> -> ServerErrorCodes.map[e.message] }
            .toList()
        request.setAttribute(AppConstants.ERRORS, ex
            .constraintViolations.stream()
            .map { obj: ConstraintViolation<*> -> obj.message }
            .collect(Collectors.joining(" ")))
        return ResponseEntity(BaseSuccessResponse(result[0]!!), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MultipartException::class)
    fun handleException(
        exception: MultipartException?,
        request: HttpServletRequest
    ): ResponseEntity<BaseSuccessResponse> {
        val baseSuccessResponse = BaseSuccessResponse()
        baseSuccessResponse.codes = List.of(ServerErrorCodes.map[ServerErrorCodes.UNKNOWN.message])
        request.setAttribute(AppConstants.ERRORS, ServerErrorCodes.UNKNOWN.message)
        return ResponseEntity.badRequest().body(baseSuccessResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception?, request: HttpServletRequest): ResponseEntity<BaseSuccessResponse> {
        val baseSuccessResponse = BaseSuccessResponse()
        baseSuccessResponse.codes = List.of(ServerErrorCodes.map[ServerErrorCodes.UNKNOWN.message])
        request.setAttribute(AppConstants.ERRORS, ServerErrorCodes.UNKNOWN.message)
        return ResponseEntity.badRequest().body(baseSuccessResponse)
    }
}
