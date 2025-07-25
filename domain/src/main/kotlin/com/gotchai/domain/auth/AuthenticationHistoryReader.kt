package com.gotchai.domain.auth

import com.gotchai.domain.global.exception.AuthenticationErrorException
import com.gotchai.domain.global.exception.AuthenticationErrorType
import org.springframework.stereotype.Component

@Component
class AuthenticationHistoryReader(
    private val authenticationHistoryRepository: AuthenticationHistoryRepository,
) {
    fun readByUserWithDeviceWithRefreshToken(
        userId: Long,
        deviceId: String?,
        refreshToken: String,
    ): AuthenticationHistory.Info =
        authenticationHistoryRepository.findUserIdWithDeviceWithRefreshToken(userId, deviceId, refreshToken)
            ?: throw AuthenticationErrorException(AuthenticationErrorType.NOT_FOUND_HISTORY)

    fun readByUserId(userId: Long): AuthenticationHistory.Info? = authenticationHistoryRepository.findUserId(userId)
}
