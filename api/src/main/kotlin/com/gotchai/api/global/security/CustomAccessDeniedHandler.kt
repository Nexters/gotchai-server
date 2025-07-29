package com.gotchai.api.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.global.dto.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class CustomAccessDeniedHandler(
    private val objectMapper: ObjectMapper
) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
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
                                errorCode = "UNAUTHORIZED_USER",
                                message = "인가되지 않은 사용자입니다."
                            )
                    )
                )
            )
        }
    }
}
