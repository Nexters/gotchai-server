package com.gotchai.domain.badge.port.out

import com.gotchai.domain.badge.entity.UserBadge

interface UserBadgeQueryPort {
    fun getUserBadgesByUserId(userId: Long): List<UserBadge>

    fun getUserBadgeByBadgeIdAndUserId(
        badgeId: Long,
        userId: Long
    ): UserBadge?
}
