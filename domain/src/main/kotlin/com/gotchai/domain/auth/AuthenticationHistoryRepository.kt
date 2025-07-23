package com.gotchai.domain.auth

interface AuthenticationHistoryRepository {
    fun create(newAuthenticationHistory: AuthenticationHistory.Creation): AuthenticationHistory.Info

    fun findUserIdWithDeviceWithRefreshToken(
        userId: Long,
        deviceId: String?,
        refreshToken: String,
    ): AuthenticationHistory.Info?

    fun update(updateAuthenticationHistory: AuthenticationHistory.Update): AuthenticationHistory.Info?

    fun findUserId(userId: Long): AuthenticationHistory.Info?

    fun removeToken(userId: Long): List<String>?

    fun remove(token: String): String
}
