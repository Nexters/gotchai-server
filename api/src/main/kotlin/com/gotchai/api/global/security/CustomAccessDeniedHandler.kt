package com.gotchai.api.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class CustomAccessDeniedHandler(
    private val objectMapper: ObjectMapper,
) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        with(response) {
            status = org.springframework.http.HttpStatus.UNAUTHORIZED.value()
            contentType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE
            writer.write(
                objectMapper.writeValueAsString(
                    com.gotchai.api.global.dto.ApiResponse.fail(
                        status = status,
                        response =
                            com.gotchai.api.global.dto.ErrorResponse(
                                errorCode = "UNAUTHORIZED_USER",
                                message = "인가되지 않은 사용자입니다.",
                            ),
                    ),
                ),
            )
        }
    }
}
