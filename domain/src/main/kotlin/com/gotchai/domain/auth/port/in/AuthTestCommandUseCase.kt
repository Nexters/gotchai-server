package com.gotchai.domain.auth.port.`in`

import com.gotchai.domain.auth.dto.command.LoginCommand
import com.gotchai.domain.auth.dto.result.LoginResult

interface AuthTestCommandUseCase {
    fun testLogin(command: LoginCommand): LoginResult

    fun testSignUp(
        name: String,
        email: String,
        password: String
    )
}
