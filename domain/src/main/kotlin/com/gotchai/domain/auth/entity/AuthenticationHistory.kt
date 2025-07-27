package com.gotchai.domain.auth.entity

import com.gotchai.domain.auth.dto.TokenPair
import java.time.LocalDateTime

class AuthenticationHistory {
    data class Creation(
        val userId: Long,
        val deviceId: String?,
        val tokenPair: TokenPair,
        val status: TokenStatus,
    )

    data class Info(
        val authenticationId: Long,
        val deviceId: String?,
        val tokenPair: TokenPair,
        val status: TokenStatus,
        val loggedInAt: LocalDateTime,
    )

    data class Update(
        val userId: Long,
        val deviceId: String?,
        val refreshToken: String,
        val tokenPair: TokenPair,
    )
}
