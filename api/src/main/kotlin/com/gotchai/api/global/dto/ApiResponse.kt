package com.gotchai.api.global.dto

import java.time.LocalDateTime

data class ApiResponse<T>(
    val isSuccess: Boolean,
    val status: Int,
    val data: T? = null,
    val timestamp: LocalDateTime
) {
    companion object {
        fun <T> success(
            status: Int,
            data: T
        ): ApiResponse<T> = ApiResponse(true, status, data, LocalDateTime.now())

        fun fail(
            status: Int,
            response: ErrorResponse
        ): ApiResponse<ErrorResponse> = ApiResponse(false, status, response, LocalDateTime.now())
    }
}
