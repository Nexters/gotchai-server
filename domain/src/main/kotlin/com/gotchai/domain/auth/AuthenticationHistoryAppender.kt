package com.gotchai.domain.auth

import org.springframework.stereotype.Component

@Component
class AuthenticationHistoryAppender(
    private val authenticationHistoryRepository: AuthenticationHistoryRepository,
) {
    fun write(newAuthenticationHistory: AuthenticationHistory.Creation) {
        authenticationHistoryRepository.create(newAuthenticationHistory)
    }
}
