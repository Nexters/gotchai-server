package com.gotchai.domain.badge.port.out

import com.gotchai.domain.badge.entity.Badge

interface BadgeQueryPort {
    fun getBadgeById(id: Long): Badge.Info?

    fun getBadgesByIdIn(ids: Collection<Long>): List<Badge.Info>
}
