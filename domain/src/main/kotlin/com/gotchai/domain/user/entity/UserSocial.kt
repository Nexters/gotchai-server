package com.gotchai.domain.user.entity

import java.time.LocalDateTime

data class UserSocial(
    val id: Long,
    val userId: Long,
    val socialId: String,
    val provider: SocialProvider,
    val createdAt: LocalDateTime,
) {
    data class Creation(
        val userId: Long,
        val socialId: String,
        val provider: SocialProvider,
    )
}
