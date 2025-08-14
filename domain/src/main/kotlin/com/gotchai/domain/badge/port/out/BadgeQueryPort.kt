package com.gotchai.domain.badge.port.out

import com.gotchai.domain.badge.dto.projection.BadgeWithAcquiredAt
import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.Tier

interface BadgeQueryPort {
    fun getBadgeById(id: Long): Badge?

    fun getBadgeByExamIdAndTier(
        examId: Long,
        badgeTier: Tier
    ): Badge?

    fun getBadgesWithAcquiredAtByUserId(userId: Long): List<BadgeWithAcquiredAt>
}
