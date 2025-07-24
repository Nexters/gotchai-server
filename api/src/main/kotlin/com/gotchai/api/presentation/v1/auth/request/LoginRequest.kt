package com.gotchai.api.presentation.v1.auth.request

data class LoginRequest(
    val email: String,
    val password: String,
)
