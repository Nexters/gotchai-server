package com.gotchai.domain.auth.port.out

import com.gotchai.domain.auth.entity.AuthenticationHistory

interface AuthenticationHistoryQueryPort {
    fun findUserId(userId: Long): AuthenticationHistory.Info?

    fun findUserIdWithDeviceWithRefreshToken(
        userId: Long,
        deviceId: String?,
        refreshToken: String,
    ): AuthenticationHistory.Info?
}
