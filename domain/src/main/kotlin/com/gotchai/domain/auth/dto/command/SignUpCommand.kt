package com.gotchai.domain.auth.dto.command

data class SignUpCommand(
    val email: String,
    val password: String?,
)
