package com.gotchai.domain.auth.port.`in`

import com.gotchai.domain.auth.dto.command.LoginCommand
import com.gotchai.domain.auth.dto.command.RefreshCommand
import com.gotchai.domain.auth.dto.command.SignUpCommand
import com.gotchai.domain.auth.dto.command.SocialLoginCommand
import com.gotchai.domain.auth.dto.result.LoginResult
import com.gotchai.domain.auth.dto.result.RefreshResult
import com.gotchai.domain.auth.dto.result.SocialLoginResult
import com.gotchai.domain.user.entity.User

interface AuthCommandUseCase {
    fun login(
        deviceId: String?,
        command: LoginCommand
    ): LoginResult

    fun socialLogin(
        deviceId: String?,
        command: SocialLoginCommand
    ): SocialLoginResult

    fun signUp(command: SignUpCommand): User

    fun refresh(
        deviceId: String?,
        command: RefreshCommand
    ): RefreshResult

    fun logout(userId: Long)
}
