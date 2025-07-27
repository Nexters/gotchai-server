package com.gotchai.domain.auth.port.out

import com.gotchai.domain.auth.entity.AuthenticationHistory

interface AuthenticationHistoryCommandPort {
    fun create(newAuthenticationHistory: AuthenticationHistory.Creation): AuthenticationHistory

    fun update(updateAuthenticationHistory: AuthenticationHistory.Update): AuthenticationHistory?

    fun removeToken(userId: Long): List<String>

    fun remove(token: String): String
}
