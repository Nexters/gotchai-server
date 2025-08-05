package com.gotchai.domain.badge.port.`in`

import com.gotchai.domain.badge.dto.result.GetMyBadgeResult
import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.Tier

interface BadgeQueryUseCase {
    fun getBadgeById(id: Long): Badge

    fun getMyBadges(userId: Long): List<GetMyBadgeResult>

    fun getBadgeByExamIdAndTier(
        examId: Long,
        badgeTier: Tier
    ): Badge
}
