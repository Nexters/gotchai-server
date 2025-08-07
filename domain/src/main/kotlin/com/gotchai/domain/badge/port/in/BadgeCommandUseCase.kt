package com.gotchai.domain.badge.port.`in`

import com.gotchai.domain.badge.entity.UserBadge

interface BadgeCommandUseCase {
    fun createUserBadge(creation: UserBadge.Creation)
}
