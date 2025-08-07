package com.gotchai.domain.badge.port.`in`

import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.Tier

interface BadgeQueryUseCase {
    fun getBadgeById(badgeId: Long): Badge

    fun getMyBadges(userId: Long): List<Badge>

    fun getBadgeByExamIdAndTier(
        examId: Long,
        tier: Tier
    ): Badge
}
