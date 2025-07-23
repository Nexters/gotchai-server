package com.gotchai.domain.auth

import com.gotchai.common.enum.auth.TokenStatus
import java.time.LocalDateTime

class AuthenticationHistory {
    data class Creation(
        val userId: Long,
        val deviceId: String?,
        val token: Token,
        val status: TokenStatus,
    )

    data class Info(
        val authenticationId: Long,
        val deviceId: String?,
        val token: Token,
        val status: TokenStatus,
        val loggedInAt: LocalDateTime,
    )

    data class Update(
        val userId: Long,
        val deviceId: String?,
        val refreshToken: String,
        val token: Token,
    )
}
