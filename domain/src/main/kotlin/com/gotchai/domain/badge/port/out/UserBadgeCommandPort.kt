package com.gotchai.domain.badge.port.out

import com.gotchai.domain.badge.entity.UserBadge

interface UserBadgeCommandPort {
    fun createUserBadge(creation: UserBadge.Creation): UserBadge

    fun deleteUserBadgeById(id: Long)
}
