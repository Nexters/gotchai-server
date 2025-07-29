package com.gotchai.domain.auth.entity

import java.time.Duration

data class RefreshToken(
    val userId: Long,
    val deviceId: String?,
    val content: String,
    val expiration: Duration,
) {
    data class Creation(
        val userId: Long,
        val deviceId: String?,
        val content: String,
        val expiration: Duration,
    )
}
