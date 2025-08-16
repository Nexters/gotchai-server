package com.gotchai.domain.badge.dto.projection

import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.Tier
import com.gotchai.domain.badge.entity.UserBadge
import java.time.LocalDateTime

data class BadgeWithAcquiredAt(
    val id: Long,
    val examId: Long,
    val name: String,
    val description: String,
    val image: String,
    val tier: Tier,
    val createdAt: LocalDateTime,
    val acquiredAt: LocalDateTime
) {
    companion object {
        fun of(
            badge: Badge,
            userBadge: UserBadge
        ): BadgeWithAcquiredAt =
            with(badge) {
                BadgeWithAcquiredAt(
                    id = id,
                    examId = examId,
                    name = name,
                    description = description,
                    image = image,
                    tier = tier,
                    createdAt = createdAt,
                    acquiredAt = userBadge.createdAt
                )
            }
    }
}
