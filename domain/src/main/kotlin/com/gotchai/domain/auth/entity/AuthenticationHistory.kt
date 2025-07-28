package com.gotchai.domain.auth.entity

import java.time.LocalDateTime

data class AuthenticationHistory(
    val id: Long,
    val userId: Long,
    val deviceId: String?,
    val status: TokenStatus,
    val loggedInAt: LocalDateTime,
) {
    data class Creation(
        val userId: Long,
        val deviceId: String?,
        val status: TokenStatus,
    )
}
