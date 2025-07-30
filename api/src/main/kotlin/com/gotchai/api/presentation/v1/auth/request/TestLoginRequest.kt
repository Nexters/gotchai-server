package com.gotchai.api.presentation.v1.auth.request

import com.gotchai.domain.auth.dto.command.LoginCommand

data class TestLoginRequest(
    val email: String,
    val password: String
) {
    fun toCommand(): LoginCommand =
        LoginCommand(
            email = email,
            password = password
        )
}
