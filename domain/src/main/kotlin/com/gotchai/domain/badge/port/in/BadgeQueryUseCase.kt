package com.gotchai.domain.badge.port.`in`

import com.gotchai.domain.badge.dto.result.GetMyBadgesResult
import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.Tier

interface BadgeQueryUseCase {
    fun getBadgeById(badgeId: Long): Badge

    fun getMyBadges(userId: Long): GetMyBadgesResult

    fun getBadgeByExamIdAndTier(
        examId: Long,
        tier: Tier
    ): Badge
}
