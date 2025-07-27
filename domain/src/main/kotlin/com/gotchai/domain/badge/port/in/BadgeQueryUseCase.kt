package com.gotchai.domain.badge.port.`in`

import com.gotchai.domain.badge.dto.result.GetMyBadgeResult
import com.gotchai.domain.badge.entity.Badge

interface BadgeQueryUseCase {
    fun getBadgeById(id: Long): Badge.Info

    fun getMyBadges(userId: Long): List<GetMyBadgeResult>
}
