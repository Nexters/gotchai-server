package com.gotchai.domain.badge.dto.result

import com.gotchai.domain.badge.dto.projection.BadgeWithAcquiredAt

data class GetMyBadgesResult(
    val badges: List<BadgeWithAcquiredAt>,
    val totalBadgeCount: Long
)
