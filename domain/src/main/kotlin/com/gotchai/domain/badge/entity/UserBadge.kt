package com.gotchai.domain.badge.entity

import java.time.LocalDateTime

data class UserBadge(
    val id: Long,
    val userId: Long,
    val badgeId: Long,
    val createdAt: LocalDateTime,
) {
    data class Creation(
        val userId: Long,
        val badgeId: Long,
    )
}
