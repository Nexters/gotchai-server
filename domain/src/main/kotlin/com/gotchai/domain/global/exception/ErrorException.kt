package com.gotchai.domain.global.exception

data class ErrorException(
    val errorType: ErrorType,
    val data: Any? = null,
    override val cause: Throwable? = null,
) : RuntimeException(errorType.message, cause)
