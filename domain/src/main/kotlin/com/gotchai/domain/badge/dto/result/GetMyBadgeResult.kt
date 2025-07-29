package com.gotchai.domain.badge.dto.result

import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.Tier
import com.gotchai.domain.badge.entity.UserBadge
import java.time.LocalDateTime

data class GetMyBadgeResult(
    val id: Long,
    val examId: Long,
    val name: String,
    val description: String,
    val image: String,
    val tier: Tier,
    val acquiredAt: LocalDateTime
) {
    companion object {
        fun of(
            badge: Badge,
            userBadge: UserBadge
        ): GetMyBadgeResult =
            GetMyBadgeResult(
                id = badge.id,
                examId = badge.examId,
                name = badge.name,
                description = badge.description,
                image = badge.image,
                tier = badge.tier,
                acquiredAt = userBadge.createdAt
            )
    }
}
