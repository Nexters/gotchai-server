package com.gotchai.domain.auth.dto.command

data class LoginCommand(
    val email: String,
    val password: String
)
