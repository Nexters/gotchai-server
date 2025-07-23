package com.gotchai.domain.global.exception

data class AuthenticationErrorException(
    val authenticationErrorType: AuthenticationErrorType,
    val data: Any? = null,
) : RuntimeException(authenticationErrorType.message)
