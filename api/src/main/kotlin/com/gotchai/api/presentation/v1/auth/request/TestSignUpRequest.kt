package com.gotchai.api.presentation.v1.auth.request

import com.gotchai.domain.auth.dto.command.SignUpCommand

data class TestSignUpRequest(
    val email: String,
    val password: String
) {
    fun toCommand(): SignUpCommand =
        SignUpCommand(
            email = email,
            password = password
        )
}
