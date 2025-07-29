package com.gotchai.api.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.global.dto.ErrorResponse
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
            status = HttpStatus.FORBIDDEN.value()
            contentType = MediaType.APPLICATION_JSON_VALUE
            writer.write(
                objectMapper.writeValueAsString(
                    ApiResponse.fail(
                        status = status,
                        response =
                            ErrorResponse(
                                errorCode = "UNAUTHENTICATED_USER",
                                message = "인증되지 않은 사용자입니다.",
                            ),
                    ),
                ),
            )
        }
    }
}
