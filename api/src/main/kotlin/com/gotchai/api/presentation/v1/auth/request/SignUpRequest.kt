package com.gotchai.api.presentation.v1.auth.request

data class SignUpRequest(
    val name: String,
    val password: String,
    val email: String,
)
