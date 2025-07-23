package com.gotchai.api.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.global.dto.ErrorResponse
import com.gotchai.domain.global.exception.ErrorType
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authenticationException: AuthenticationException,
    ) {
        with(response) {
            status = HttpStatus.UNAUTHORIZED.value()
            contentType = MediaType.APPLICATION_JSON_VALUE
            writer.write(
                objectMapper.writeValueAsString(
                    ApiResponse.fail(
                        status = HttpStatus.UNAUTHORIZED.value(),
                        response =
                            ErrorResponse(
                                errorCode = ErrorType.UNAUTHORIZED_TOKEN.name,
                                message = ErrorType.UNAUTHORIZED_TOKEN.message,
                            ),
                    ),
                ),
            )
        }
    }
}
