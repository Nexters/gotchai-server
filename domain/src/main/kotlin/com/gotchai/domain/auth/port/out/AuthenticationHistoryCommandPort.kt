package com.gotchai.domain.auth.port.out

import com.gotchai.domain.auth.entity.AuthenticationHistory

interface AuthenticationHistoryCommandPort {
    fun createAuthenticationHistory(creation: AuthenticationHistory.Creation): AuthenticationHistory
}
