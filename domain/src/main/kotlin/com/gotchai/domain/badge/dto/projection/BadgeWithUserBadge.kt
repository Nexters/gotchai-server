package com.gotchai.domain.badge.dto.projection

import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.UserBadge

data class BadgeWithUserBadge(
    val badge: Badge,
    val userBadge: UserBadge
)
