package com.gotchai.domain.auth.port.`in`

import com.gotchai.domain.auth.dto.command.RefreshCommand
import com.gotchai.domain.auth.dto.command.SocialLoginCommand
import com.gotchai.domain.auth.dto.result.RefreshResult
import com.gotchai.domain.auth.dto.result.SocialLoginResult

interface AuthCommandUseCase {
    fun socialLogin(
        deviceId: String?,
        command: SocialLoginCommand,
    ): SocialLoginResult

    fun refresh(deviceId: String?, command: RefreshCommand): RefreshResult

    fun logout(userId: Long)
}
