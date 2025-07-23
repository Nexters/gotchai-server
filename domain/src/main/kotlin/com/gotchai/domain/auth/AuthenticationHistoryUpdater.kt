package com.gotchai.domain.auth

import com.gotchai.domain.global.exception.AuthenticationErrorException
import com.gotchai.domain.global.exception.AuthenticationErrorType
import org.springframework.stereotype.Component

@Component
class AuthenticationHistoryUpdater(
    private val authenticationHistoryRepository: AuthenticationHistoryRepository,
) {
    fun update(updateAuthenticationHistory: AuthenticationHistory.Update): AuthenticationHistory.Info =
        authenticationHistoryRepository.update(updateAuthenticationHistory)
            ?: throw AuthenticationErrorException(AuthenticationErrorType.NOT_FOUND_HISTORY)

    fun removeToken(userId: Long): List<String> =
        authenticationHistoryRepository.removeToken(userId)
            ?: throw AuthenticationErrorException(AuthenticationErrorType.NOT_FOUND_HISTORY)

    fun remove(token: String): String = authenticationHistoryRepository.remove(token)
}
