package com.gotchai.domain.badge.dto.result

import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.Rank
import com.gotchai.domain.badge.entity.UserBadge
import java.time.LocalDateTime

data class GetMyBadgeResult(
    val id: Long,
    val examId: Long,
    val name: String,
    val description: String,
    val image: String,
    val rank: Rank,
    val acquiredAt: LocalDateTime,
) {
    companion object {
        fun of(badge: Badge, userBadge: UserBadge): GetMyBadgeResult =
            GetMyBadgeResult(
                id = badge.id,
                examId = badge.examId,
                name = badge.name,
                description = badge.description,
                image = badge.image,
                rank = badge.rank,
                acquiredAt = userBadge.createdAt
            )
    }
}
