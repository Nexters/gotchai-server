package com.gotchai.api.global.exception

import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.global.dto.ErrorResponse
import com.gotchai.common.util.logger
import com.gotchai.domain.global.exception.ServerException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice(basePackages = ["com.gotchai"])
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    companion object {
        private val log by logger()
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        log.error("MethodArgumentNotValidException : {}", ex.message, ex)

        val message =
            ex.bindingResult.allErrors
                .mapNotNull { it.defaultMessage }
                .joinToString("; ")
                .ifEmpty { "Validation failed" }
        val errorResponse =
            ErrorResponse(
                errorCode = ex.getErrorCode(),
                message = message
            )
        val apiResponse = ApiResponse.fail(status.value(), errorResponse)

        return ResponseEntity
            .status(apiResponse.status)
            .body(apiResponse)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(
        ex: ConstraintViolationException
    ): ResponseEntity<ApiResponse<ErrorResponse>> {
        log.error("ConstraintViolationException: {}", ex.message, ex)
        val violations =
            ex.constraintViolations.associate {
                it.propertyPath.toString().substringAfterLast(".", "unknown") to it.message
            }
        val errorResponse =
            ErrorResponse(
                errorCode = ex.getErrorCode(),
                message = violations.toString()
            )
        val apiResponse = ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), errorResponse)

        return ResponseEntity
            .status(apiResponse.status)
            .body(apiResponse)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(
        ex: MethodArgumentTypeMismatchException
    ): ResponseEntity<ApiResponse<ErrorResponse>> {
        log.error("MethodArgumentTypeMismatchException : {}", ex.message, ex)

        val errorResponse =
            ErrorResponse(
                errorCode = "METHOD_ARGUMENT_TYPE_MISMATCH",
                message = "요청 한 값 타입이 잘못되어 binding에 실패하였습니다."
            )
        val apiResponse = ApiResponse.fail(400, errorResponse)

        return ResponseEntity
            .status(apiResponse.status)
            .body(apiResponse)
    }

    override fun handleHttpRequestMethodNotSupported(
        ex: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        log.error("HttpRequestMethodNotSupportedException : {}", ex.message, ex)

        val errorResponse =
            ErrorResponse(
                errorCode = ex.getErrorCode(),
                message = "지원하지 않는 HTTP method 입니다."
            )
        val apiResponse = ApiResponse.fail(400, errorResponse)

        return ResponseEntity
            .status(apiResponse.status)
            .body(apiResponse)
    }

    @ExceptionHandler(ServerException::class)
    fun handleServerException(ex: ServerException): ResponseEntity<ApiResponse<ErrorResponse>> {
        log.error("gotchai CustomException : {}", ex.message, ex)

        val errorResponse =
            ErrorResponse(
                errorCode = ex.getErrorCode(),
                message = ex.message
            )
        val apiResponse = ApiResponse.fail(ex.status, errorResponse)

        return ResponseEntity
            .status(ex.status)
            .body(apiResponse)
    }

    @ExceptionHandler(Exception::class)
    protected fun handleException(ex: Exception): ResponseEntity<ApiResponse<ErrorResponse>> {
        log.error("Internal Server Error : {}", ex.message, ex)

        val errorResponse =
            ErrorResponse(
                errorCode = "INTERNAL_SERVER_ERROR",
                message = "서버 오류, 관리자에게 문의하세요"
            )
        val apiResponse = ApiResponse.fail(500, errorResponse)

        return ResponseEntity
            .status(apiResponse.status)
            .body(apiResponse)
    }

    private fun Exception.getErrorCode(): String =
        this::class.simpleName?.run {
            replace(Regex("([a-z])([A-Z])"), "$1_$2")
                .uppercase()
                .removeSuffix("_EXCEPTION")
        } ?: "INTERNAL_SERVER_ERROR"
}
