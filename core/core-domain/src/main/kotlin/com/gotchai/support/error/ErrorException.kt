package com.gotchai.support.error

data class ErrorException(
    val errorType: ErrorType,
    val data: Any? = null,
    override val cause: Throwable? = null,
) : RuntimeException(errorType.message, cause)
