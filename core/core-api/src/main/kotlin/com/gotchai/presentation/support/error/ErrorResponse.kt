package com.gotchai.presentation.support.error

data class ErrorResponse(
    val errorCode: String,
    val message: String,
) {
    companion object {
        fun of(
            errorCode: String,
            message: String,
        ) = ErrorResponse(
            errorCode = errorCode,
            message = message,
        )
    }
}
